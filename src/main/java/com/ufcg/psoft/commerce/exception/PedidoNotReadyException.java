package com.ufcg.psoft.commerce.exception;

public class PedidoNotReadyException extends CommerceException{
    public PedidoNotReadyException(){
        super("O pedido não está na etapa pedido pronto");
    }
}
