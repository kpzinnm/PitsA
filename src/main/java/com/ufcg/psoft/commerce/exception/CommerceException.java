package com.ufcg.psoft.commerce.exception;

public class CommerceException extends RuntimeException {
    public CommerceException() {
        super("Erro inesperado no AppCommerce!");
    }

    private String error;

    public CommerceException(String message) {
        super(message);
    }

   /* public CommerceException(String message) {
        super(message);
    }*/

    public String getError(){
        return this.error;
    }

    public void serError(String error){
        this.error = error;
    }

}
