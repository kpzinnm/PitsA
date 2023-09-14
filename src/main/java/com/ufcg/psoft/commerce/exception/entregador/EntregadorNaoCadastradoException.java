package com.ufcg.psoft.commerce.exception.entregador;
import br.edu.ufcg.computacao.psoft.commerce.exception.CommerceException;
public class EntregadorNaoCadastradoException extends CommerceException {
    public EntregadorNaoCadastradoException(){
        super("Entregador não cadstrado exception");
    };
}
