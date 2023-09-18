package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Associacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociacaoRepository extends JpaRepository<Associacao, Long> {
    Optional<Associacao> findByEntregadorIdAndEstabelecimentoId(Long entregadorId, Long estabelecimentoId);
}
