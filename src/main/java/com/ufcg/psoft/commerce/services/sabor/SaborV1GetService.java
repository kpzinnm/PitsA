package com.ufcg.psoft.commerce.services.sabor;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoCardapioDTO;
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
    public List<Sabor> getAllCardapio(Long id) {
        List<Sabor> sabores = new ArrayList<Sabor>();

        if (estabelecimentoRepository.existsById(id)) {

            Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).get();

            sabores.addAll(estabelecimento.getSabores());

            sabores = (List<Sabor>) modelMapper.map(sabores, EstabelecimentoCardapioDTO.class);

            return sabores;
        } else {
            throw new EstabelecimentoNaoExisteException();
        }

    }

    @Override
    public List<Sabor> getTipo(Long id, String tipo) {
        // if (estabelecimentoRepository.existsById(id)) {
        //     List<Sabor> sabores = new ArrayList<Sabor>();
        //     List<Sabor> saboresFinais = new ArrayList<Sabor>();

        //     Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).get();

        //     sabores.addAll(estabelecimento.getSabores());

        //     for (Sabor sabor : sabores) {
        //         if (sabor.getTipo().equals(tipo)) {
        //             saboresFinais.add(sabor);
        //         }
        //     }

        //     return saboresFinais;
        // }
        // return null;

        return saborRepository.findByTipo(tipo);
    }

}
