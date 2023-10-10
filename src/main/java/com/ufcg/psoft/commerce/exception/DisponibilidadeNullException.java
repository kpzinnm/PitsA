package com.ufcg.psoft.commerce.exception;

public class DisponibilidadeNullException extends CommerceException { 
    public DisponibilidadeNullException() {
        super("O valor da disponibilidade nao pode ser nulo");
    }

    public DisponibilidadeNullException(String message) {
        super(message);
    }
}
