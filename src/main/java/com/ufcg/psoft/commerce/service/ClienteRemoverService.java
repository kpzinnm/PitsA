package com.ufcg.psoft.commerce.service;

@FunctionalInterface
public interface ClienteRemoverService {

    public void removeCliente(Long id, String codigoAcesso);
}
