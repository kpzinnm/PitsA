package com.ufcg.psoft.commerce.exception;

public class SaborJaEstaDisponivelException extends CommerceException{
    public SaborJaEstaDisponivelException() {
        super("O sabor consultado ja esta disponivel!");
    }
}
