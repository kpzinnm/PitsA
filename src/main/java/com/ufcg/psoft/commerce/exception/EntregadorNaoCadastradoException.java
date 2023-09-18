package com.ufcg.psoft.commerce.exception;
public class EntregadorNaoCadastradoException extends CommerceException {
    public EntregadorNaoCadastradoException(){
        super("Entregador não cadastrado");
    };

    public EntregadorNaoCadastradoException(String message) {
        super(message);
    }
}
