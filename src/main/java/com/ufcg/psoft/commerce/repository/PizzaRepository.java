package com.ufcg.psoft.commerce.repository;


import com.ufcg.psoft.commerce.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long>{
}