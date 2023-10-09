package com.ufcg.psoft.commerce.repository;


import com.ufcg.psoft.commerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findAllByClienteId(Long clienteId);
    List<Pedido> findAllByEstabelecimentoId(Long estabelecimentoId);
}