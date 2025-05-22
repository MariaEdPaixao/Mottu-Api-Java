package br.com.fiap.mottu_api.service;

import br.com.fiap.mottu_api.model.DTO.HistoricoFilialDTO;
import br.com.fiap.mottu_api.model.DTO.MotoNaFilialDTO;
import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.repository.FilialRepository;
import br.com.fiap.mottu_api.repository.HistoricoMotoFilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class FilialService {

    @Autowired
    FilialRepository filialRepository;

    @Autowired
    HistoricoMotoFilialRepository historicoRepository;

    public HistoricoFilialDTO getHistoricoPorFilial(Long id) {
        Filial filial = filialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Filial não encontrada"));

        var motos = historicoRepository.findAll().stream()
                .filter(h -> h.getFilial().getId().equals(id))
                .map(h -> new MotoNaFilialDTO(
                        h.getMoto().getPlaca(),
                        h.getMoto().getModeloMoto().getModelo(),
                        h.getDataMovimentacao()
                ))
                .toList();

        return new HistoricoFilialDTO(filial.getNomeFilial(), motos);
    }
}
