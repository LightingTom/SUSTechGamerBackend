package com.example.dao;

import com.example.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Dao extends JpaRepository<Test,Integer> {
    List<Test> findAll();
    Test findByName(String name);

    @Override
    Test saveAndFlush(Test s);

}
