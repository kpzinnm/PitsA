package com.ufcg.psoft.commerce.exception;

public class PedidoNotReceivedException extends CommerceException{
    public PedidoNotReceivedException(){
        super("O pedido não está na etapa pedido recebido");
    }
}
