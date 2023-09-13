package com.ufcg.psoft.commerce.exception;

public class EstabelecimentoCodigoAcessoDiferenteException extends CommerceException{

    public EstabelecimentoCodigoAcessoDiferenteException(){
        super("Código de acesso não corresponde com o estabelecimento.");
    }
}
