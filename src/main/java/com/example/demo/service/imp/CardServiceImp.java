package com.example.demo.service.imp;


import com.example.demo.entity.Card;
import com.example.demo.repository.CardRepository;
import com.example.demo.service.CardService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CardServiceImp implements CardService {

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String,Object> body = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(CardServiceImp.class);

    @Value("${user.home}")
    private String homePath;

    @Value("${card.url1}")
    private String url1;

    @Value("${card.url2}")
    private String url2;

    @Autowired
    private CardRepository cardRepository;

    private Map<String,String> abbre = new HashMap<String,String>(){{
       put("京东钢镚","JDGB");
       put("京东礼品","GDLP");
       put("苏宁礼品","SNLP");
       put("苏宁家电","SNJD");
       put("苏宁铜板","SNTB");
       put("苏宁超市","SNCS");
    }};

    private Pattern pattern = Pattern.compile("^.*?([0-9]+).*$");


    @PostConstruct
    public void buildRequestBody() {
        body.put("q","");
        body.put("p",1);
        body.put("size",Integer.MAX_VALUE);
    }


    @Scheduled(fixedRate = 30000)
    @Override
    public void syncCardInfo() {
        try {
            List<String> referers = objectMapper.readValue(new File(homePath+"/config.json"), new TypeReference<List<String>>() {
            });
            for (String referer : referers) {
                cardRepository.removeByOriginType(referer);
               Map map = fetchTitle(referer);
               if(map == null) {
                   return;
               }

               List<Card> list = fetchCard(referer);
               decorateCardList(referer,list,map);
               for(Card card: list) {
                   cardRepository.save(card);
               }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private List<Card> decorateCardList(String referer, List<Card> cards,Map map) {
        String[] array = referer.split("\\.");
        String title = (String) map.get("Title");
        for(Card card : cards) {
            card.setOrigin(title);
            card.setOriginUrl(referer);
            card.setOriginType(referer);
            card.setStandardPrice(judgeStandardPrice(card.getName()));
            card.setBelong(judgeBelong(card.getName()));
            card.setDate_modified(new Date());
            card.setDate_created(new Date());
        }
        return cards;
    }

    private BigDecimal judgeStandardPrice(String name) {
        name = name.replace("3C","");
       Matcher matcher =  pattern.matcher(name);
       if(matcher.matches()) {
           String val = matcher.group(1);
          return new BigDecimal(val);
       }
       return BigDecimal.ZERO;
    }

    private String judgeBelong(String name) {
       for(Map.Entry<String,String> entry : abbre.entrySet()) {
           if(name.contains(entry.getKey())) {
               return entry.getValue();
           }
       }
       return "UNKNOWN";
    }

    private Map fetchTitle(String referer) {
        HttpEntity<Map> httpEntity = new HttpEntity<>(body, createHttpHeader(referer));
        ResponseEntity<Map> response = restTemplate.exchange(url1, HttpMethod.GET, httpEntity, Map.class);
        if(!response.getStatusCode().is2xxSuccessful()) {
            logger.error("{} - 获取店铺信息出错 ",url1);
            return null;
        }
        Map map = response.getBody();
        boolean result = (boolean) map.get("IsSuccess");
        if(!result) {
            logger.error("{} - 获取店铺信息出错 - {}",url1,response);
            return null;
        }
        return (Map) map.get("Data");
    }

    private List<Card> fetchCard(String referer) {
        HttpEntity<Map> httpEntity = new HttpEntity<>(body, createHttpHeader(referer));
        ResponseEntity<Map> response = restTemplate.exchange(url2, HttpMethod.POST, httpEntity, Map.class);
        if(!response.getStatusCode().is2xxSuccessful()) {
            logger.error("{} - 获取购物卡详情出错 ",url2);
            return Collections.emptyList();
        }
        Map map = response.getBody();
        boolean result = (boolean) map.get("IsSuccess");
        if(!result) {
            logger.error("{} - 获取购物卡详情出错 - {}",url2,response);
            return Collections.emptyList();

        }
        Map data = (Map)map.get("Data");
        List<Map> goods = (List<Map>) data.get("Goods");
        Map<String,Integer> stock = (Map<String,Integer>) data.get("StockNumsDict");
        return convertCard(goods,stock);
    }


    private List<Card> convertCard(List<Map> goods,Map<String,Integer> stock) {
        List<Card> list = new ArrayList<>();
        for(Map item : goods) {
            Card card = new Card();
            String name = (String) item.get("Name");
            if(name.contains("勿拍") || name.contains("授权")) {
                continue;
            }
            card.setPrice(BigDecimal.valueOf((Double) item.get("Price")));
            card.setCardId((Integer) item.get("SpId"));
            card.setName(name);
            card.setDescription((String) item.get("Summary"));
            list.add(card);
        }
        for(Card item : list) {
            int num = stock.get(Integer.toString(item.getCardId()));
            item.setStock(num);
        }
        return list;
    }

    private HttpHeaders createHttpHeader(String referer) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("referer",referer);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;

    }

    @Override
    public List<Card> getCardInfo() {
        return cardRepository.findAll();
    }
}
