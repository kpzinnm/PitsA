package com.ufcg.psoft.commerce.services.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.exception.EntregadorNaoCadastradoException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntregadorV1PegarService implements EntregadorPegarService{
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EntregadorRepository entregadorRepository;

    @Override
    public EntregadorGetRequestDTO pegaEntregador(Long idEndereco) {
        if(entregadorRepository.existsById(idEndereco)){
            Entregador entregador = entregadorRepository.findById(idEndereco).get();
            return modelMapper.map(entregador, EntregadorGetRequestDTO.class);
        }
        else{
            throw new EntregadorNaoCadastradoException();
        }
    }

    @Override
    public List<EntregadorGetRequestDTO> PegaTodosEntregadores() {
        List<Entregador> entregadores = entregadorRepository.findAll();
        List<EntregadorGetRequestDTO> entregadoresResponse = new ArrayList<>();

        for(Entregador entregador: entregadores){
            entregadoresResponse.add(modelMapper.map(entregador, EntregadorGetRequestDTO.class));
        }
        return entregadoresResponse;
    }
}
