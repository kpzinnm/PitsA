package com.ufcg.psoft.commerce.services.sabor;

import java.util.List;


import com.ufcg.psoft.commerce.model.Sabor;

public interface SaborGetService {
    public List<Sabor> getAll(Long estabelecimentoID, String codigoAcesso);
    public Sabor getSaborById(Long id, Long estabelecimentoID, String codigoAcesso);
}
