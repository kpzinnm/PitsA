package com.ufcg.psoft.commerce.exception;

public class PedidoNotPreparedException extends CommerceException{
    public PedidoNotPreparedException(){
        super("O pedido não está na etapa Pedido em preparo");
    }
}
