package com.ufcg.psoft.commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.commerce.model.Sabor;

public interface SaborRepository extends JpaRepository<Sabor, Long>{
    public List<Sabor> findByTipo(String tipo);
}