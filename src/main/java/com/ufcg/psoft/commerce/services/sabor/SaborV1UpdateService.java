package com.ufcg.psoft.commerce.services.sabor;

import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoNotificarDisponibilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.SaborJaEstaDisponivelException;
import com.ufcg.psoft.commerce.exception.SaborJaEstaIndisponivelException;
import com.ufcg.psoft.commerce.exception.SaborNotExistException;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;


@Service
public class SaborV1UpdateService implements SaborUpdateService {

    @Autowired
    EstabelecimentoValidar estabelecimentoValidar;

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    SaborVerificaTipoService saborVerificaTipoService;

    @Autowired
    private EstabelecimentoNotificarDisponibilidadeService estabelecimentoNotificarDisponibilidadeService;

    @Override
    public Sabor updateById(SaborPostPutRequestDTO saborPostPutRequestDTO, Long saborId, Long estabelecimentoId,
            String estabelecimentoCodigoAcesso) {
        this.estabelecimentoValidar.validar(estabelecimentoId, estabelecimentoCodigoAcesso);

        if (saborRepository.existsById(saborId)) {
            Sabor saborUpdate = saborRepository.findById(saborId).get();

            saborVerificaTipoService.verificaTipo(saborPostPutRequestDTO.getTipo().toUpperCase());

            saborUpdate.setNome(saborPostPutRequestDTO.getNome());
            saborUpdate.setTipo(saborPostPutRequestDTO.getTipo());
            saborUpdate.setPrecoG(saborPostPutRequestDTO.getPrecoG());
            saborUpdate.setPrecoM(saborPostPutRequestDTO.getPrecoM());
            saborUpdate.setDisponivel(saborPostPutRequestDTO.getDisponivel());

            saborRepository.flush();

            return saborUpdate;
        } else {
            throw new SaborNotExistException();
        }
    }

    @Override
    public Sabor updateByIdDisponibilidade(Long saborId, Long estabelecimentoId,
            String estabelecimentoCodigoAcesso, Boolean disponibilidade) {
        this.estabelecimentoValidar.validar(estabelecimentoId, estabelecimentoCodigoAcesso);

        if (saborRepository.existsById(saborId)) {
            Sabor saborUpdate = saborRepository.findById(saborId).get();
            
            if(saborUpdate.getDisponivel() == disponibilidade && disponibilidade == true){
                throw new SaborJaEstaDisponivelException();
            }

            if(saborUpdate.getDisponivel() == disponibilidade && disponibilidade == false){
                throw new SaborJaEstaIndisponivelException();
            }

            if(disponibilidade)
                estabelecimentoNotificarDisponibilidadeService.notificarDisponibilidadeSabor(saborId);

            saborUpdate.setDisponivel(disponibilidade);

            saborRepository.flush();

            return saborUpdate;
        } else {
            throw new SaborNotExistException();
        }
    }
}
