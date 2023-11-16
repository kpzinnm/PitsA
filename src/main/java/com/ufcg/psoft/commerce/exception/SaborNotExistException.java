package com.ufcg.psoft.commerce.exception;

public class SaborNotExistException extends CommerceException{
    public SaborNotExistException(){
        super("O sabor consultado nao existe!");
    }
}
