package com.ufcg.psoft.commerce.exception;

public class TipoInvalidoException extends CommerceException {
    public TipoInvalidoException() {
        super("Tipo deve ser salgado ou doce");
    }
}
