package com.ufcg.psoft.commerce.exception;

public class SaborJaEstaIndisponivelException extends CommerceException{
    public SaborJaEstaIndisponivelException() {
        super("O sabor consultado ja esta indisponivel!");
    }
}
