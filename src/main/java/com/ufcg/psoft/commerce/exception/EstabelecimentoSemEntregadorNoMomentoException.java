package com.ufcg.psoft.commerce.exception;

public class EstabelecimentoSemEntregadorNoMomentoException extends CommerceException {
    public EstabelecimentoSemEntregadorNoMomentoException(){
        super("Estamos sem entregador no momento, realizar novamente a operação!");
    }

    public EstabelecimentoSemEntregadorNoMomentoException(String message) {
        super(message);
    }
}
