package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
