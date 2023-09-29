package com.ufcg.psoft.commerce.services.estabelecimento;

import java.util.List;

import com.ufcg.psoft.commerce.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoBuscarService {
    public List<Estabelecimento> listarTodos();
}
