package com.ufcg.psoft.commerce.exception;

public class EstabelecimentoCodigoAcessoInvalidoException extends CommerceException{

    public EstabelecimentoCodigoAcessoInvalidoException(){
        super("Erros de validacao encontrados");
       // super.serError("Codigo de acesso deve ter exatamente 6 digitos numericos");
    }
}
