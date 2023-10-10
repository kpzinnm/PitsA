package com.ufcg.psoft.commerce.services.sabor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabores.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.exception.SaborNotExistException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;

@Service
public class SaborV1GetService implements SaborGetService {

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    EstabelecimentoValidar estabelecimentoValidar;

    @Override
    public List<Sabor> getAll(Long estabelecimentoID, String codigoAcesso) {
        this.estabelecimentoValidar.validar(estabelecimentoID, codigoAcesso);

        return saborRepository.findAll();
    }

    @Override
    public Sabor getSaborById(Long id, Long estabelecimentoID, String codigoAcesso) {
        this.estabelecimentoValidar.validar(estabelecimentoID, codigoAcesso);

        if (saborRepository.existsById(id)) {
            return saborRepository.findById(id).get();
        } else {
            throw new SaborNotExistException();
        }
    }

    @Override
    public Sabor getSaborByNome(String nome) {
        Sabor sabor = saborRepository.findByNome(nome);

        if (sabor != null) {
            return sabor;
        } else {
            throw new SaborNotExistException();
        }
    }

    @Override
    public List<SaborResponseDTO> getAllCardapio(Long id) {
        if (estabelecimentoRepository.existsById(id)) {

            Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).get();
            Set<Sabor> cardapio = estabelecimento.getSabores();

            List<SaborResponseDTO> saboresValidos = cardapio.stream()
                    .filter(sabor -> sabor.isDisponivel())
                    .map(sabor -> modelMapper.map(sabor, SaborResponseDTO.class))
                    .collect(Collectors.toList());

            List<SaborResponseDTO> saboresInvalidos = cardapio.stream()
                    .filter(sabor -> !sabor.isDisponivel())
                    .map(sabor -> modelMapper.map(sabor, SaborResponseDTO.class))
                    .collect(Collectors.toList());

            saboresValidos.addAll(saboresInvalidos);

            return saboresValidos;
        } else {
            throw new EstabelecimentoNaoExisteException();
        }

    }

    @Override
    public List<Sabor> getTipo(Long id, String tipo) {
        if (estabelecimentoRepository.existsById(id)) {
            List<Sabor> sabores = new ArrayList<>();
            
            Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).get();
            
            Set<Sabor> cardapio = estabelecimento.getSabores();

            for(Sabor sabor : cardapio){
                if(sabor.getTipo().toUpperCase().equals(tipo.toUpperCase())){
                    sabores.add(sabor);
                }
            }

            return sabores;
        } else {
            throw new EstabelecimentoNaoExisteException();

        }
    }

}
