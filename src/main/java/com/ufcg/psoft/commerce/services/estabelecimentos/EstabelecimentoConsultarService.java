package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.model.Estabelecimento;

import java.util.List;
import java.util.Optional;

public interface EstabelecimentoConsultarService {

    public Optional<Estabelecimento> consultarEstabelecimento(Long id);

    public List<Estabelecimento> consultarTodosEstabelecimentos();

}
