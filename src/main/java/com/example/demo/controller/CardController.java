package com.example.demo.controller;

import com.example.demo.entity.Card;
import com.example.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping(value = "/")
    public String index(Model model) {

        return "index";
    }

    @GetMapping(value="/cards")
    public ModelAndView showCards() {

        List<Card> cards = cardService.getCardInfo();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("cards", cards);

        return new ModelAndView("cards", params);
    }


    @ResponseBody
    @GetMapping(value="/allcards",produces = APPLICATION_JSON_VALUE)
    public List<Card> showAllCards() {

        return cardService.getCardInfo();
    }


}
