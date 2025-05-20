package br.com.fiap.mottu_api.service;

import br.com.fiap.mottu_api.model.HistoricoMotoEspecificaDTO;
import br.com.fiap.mottu_api.model.ModeloMoto;
import br.com.fiap.mottu_api.model.Moto;
import br.com.fiap.mottu_api.model.MovimentacaoDTO;
import br.com.fiap.mottu_api.repository.HistoricoMotoFilialRepository;
import br.com.fiap.mottu_api.repository.ModeloMotoRepository;
import br.com.fiap.mottu_api.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private ModeloMotoRepository modeloMotoRepository;

    @Autowired
    private HistoricoMotoFilialRepository historicoRepository;

    public HistoricoMotoEspecificaDTO getHistoricoPorMoto(Long motoId) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        List<MovimentacaoDTO> movimentacoes = historicoRepository.findByMotoId(motoId)
                .stream()
                .map(h -> new MovimentacaoDTO(
                        h.getFilial().getNomeFilial(),
                        h.getDataMovimentacao()
                )).toList();

        return new HistoricoMotoEspecificaDTO(moto.getPlaca(), moto.getModeloMoto().getModelo(), movimentacoes);
    }

    public void validarMoto(Moto moto, Long idAtual) {
        motoRepository.findByPlaca(moto.getPlaca())
                .filter(m -> !m.getId().equals(idAtual))
                .ifPresent(m -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Placa já cadastrada em outra moto.");
                });

        Long modeloId = moto.getModeloMoto().getId();
        ModeloMoto modelo = modeloMotoRepository.findById(modeloId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo de moto não encontrado"));

        moto.setModeloMoto(modelo);
    }


}
