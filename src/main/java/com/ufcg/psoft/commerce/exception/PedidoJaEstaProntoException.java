package com.ufcg.psoft.commerce.exception;

public class PedidoJaEstaProntoException extends CommerceException {
    public PedidoJaEstaProntoException(){
        super("Pedido Já está pronto");
    }
    public PedidoJaEstaProntoException(String message){
        super(message);
    }
}
