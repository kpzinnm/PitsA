package com.ufcg.psoft.commerce.exception;

public class PedidoNotOnTheWayException extends CommerceException{
    public PedidoNotOnTheWayException(){
        super("O pedido não está na etapa Pedido em rota");
    }
}
