package com.ufcg.psoft.commerce.exception;

public class EntregadorTipoVeiculoInvalido extends CommerceException{
    public EntregadorTipoVeiculoInvalido(){
        super("Tipo do veiculo deve ser moto ou carro");
    }
}
