package com.example.demo.repository;

import com.example.demo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;


public interface CardRepository extends JpaRepository<Card, Integer> {

    @Transactional
    @Modifying
    void removeByOriginType(String originType);
}
