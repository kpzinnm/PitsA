package com.ufcg.psoft.commerce.services.sabor;

import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.TipoInvalidoException;

@Service
public class SaborV1VerificaTipoService implements SaborVerificaTipoService{

    @Override
    public Boolean verificaTipo(String tipo) {
        if(!(tipo.equals("doce") || tipo.equals("salgado"))){
            throw new TipoInvalidoException();
        } 

        return true;
    }
    
}
