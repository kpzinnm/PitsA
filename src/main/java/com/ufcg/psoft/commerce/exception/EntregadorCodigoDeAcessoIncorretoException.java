package com.ufcg.psoft.commerce.exception;
public class EntregadorCodigoDeAcessoIncorretoException extends CommerceException {
    public EntregadorCodigoDeAcessoIncorretoException(){
        super("Codigo de acesso não correspode com o deste entregador!");
    }

    public EntregadorCodigoDeAcessoIncorretoException(String message){
        super(message);
    }
}
