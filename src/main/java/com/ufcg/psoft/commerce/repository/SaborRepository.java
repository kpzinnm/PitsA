package com.ufcg.psoft.commerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.commerce.model.Sabor;

public interface SaborRepository extends JpaRepository<Sabor, Long>{
    // public List<Sabor> findByTipo(Long id, String tipo);
}