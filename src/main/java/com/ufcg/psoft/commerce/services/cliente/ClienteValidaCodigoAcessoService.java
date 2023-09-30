package com.ufcg.psoft.commerce.services.cliente;

@FunctionalInterface
public interface ClienteValidaCodigoAcessoService {

    public Boolean validaCodigoAcesso(Long clienteId, String codigoAcesso);
}
