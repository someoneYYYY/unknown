package com.example.demo.service;

import com.example.demo.entity.Card;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CardService {


    void syncCardInfo();

    List<Card> getCardInfo();
}
