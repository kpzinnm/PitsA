package com.ufcg.psoft.commerce.services.sabor;

import java.util.List;

import com.ufcg.psoft.commerce.dto.sabores.SaborResponseDTO;
import com.ufcg.psoft.commerce.model.Sabor;

public interface SaborGetService {
    public List<Sabor> getAll(Long estabelecimentoID, String codigoAcesso);
    public List<SaborResponseDTO> getAllCardapio(Long id);
    public List<Sabor> getTipo(Long id, String tipo);
    public Sabor getSaborById(Long id, Long estabelecimentoID, String codigoAcesso);
    public Sabor getSaborByNome(String nome);
}
