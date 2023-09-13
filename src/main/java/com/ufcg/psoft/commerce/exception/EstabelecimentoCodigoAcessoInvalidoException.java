package com.ufcg.psoft.commerce.exception;

public class EstabelecimentoCodigoAcessoInvalidoException extends CommerceException{

    public EstabelecimentoCodigoAcessoInvalidoException(){
        super("Codigo de acesso deve ter 6 digitos");
    }
}
