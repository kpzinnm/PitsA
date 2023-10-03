package com.ufcg.psoft.commerce.exception;

public class PedidoNotExistException extends CommerceException{
    public PedidoNotExistException(){
        super("O pedido consultado nao existe!");
    }
}
