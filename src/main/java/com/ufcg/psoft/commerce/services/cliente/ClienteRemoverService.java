package com.ufcg.psoft.commerce.services.cliente;

@FunctionalInterface
public interface ClienteRemoverService {

    public void removeCliente(Long id, String codigoAcesso);
}
