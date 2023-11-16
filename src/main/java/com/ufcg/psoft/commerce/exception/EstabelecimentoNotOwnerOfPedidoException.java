package com.ufcg.psoft.commerce.exception;

public class EstabelecimentoNotOwnerOfPedidoException extends CommerceException{
    public EstabelecimentoNotOwnerOfPedidoException(){
        super("O pedido consultado nao pertence a esse cliente!");
    }
}
