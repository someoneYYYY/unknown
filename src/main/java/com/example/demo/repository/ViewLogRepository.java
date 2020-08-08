package com.example.demo.repository;

import com.example.demo.entity.ViewLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ViewLogRepository extends JpaRepository<ViewLog, Integer> {

    List<ViewLog> findByDateViewGreaterThan(Date date);

    int countByDateViewGreaterThan(Date date);
}
