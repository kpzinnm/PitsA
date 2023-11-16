package com.ufcg.psoft.commerce.exception;

public class PedidoEmAndamentoException extends CommerceException{
    public PedidoEmAndamentoException(){
        super("Pedido está em andamento");
    }
    public PedidoEmAndamentoException(String message){
        super(message);
    }
}
