package com.ufcg.psoft.commerce.exception;

public class SaborIndisponivelNaoEncontradoException extends CommerceException{
    public SaborIndisponivelNaoEncontradoException() {
        super("Esse Sabor não está com o status de indisponivel");
    }
}
