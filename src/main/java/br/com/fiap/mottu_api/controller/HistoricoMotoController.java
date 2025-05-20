package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.HistoricoMotoEspecificaDTO;
import br.com.fiap.mottu_api.model.Moto;
import br.com.fiap.mottu_api.model.MotoHistoricoDTO;
import br.com.fiap.mottu_api.repository.HistoricoMotoFilialRepository;
import br.com.fiap.mottu_api.repository.MotoRepository;
import br.com.fiap.mottu_api.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/moto")
public class HistoricoMotoController {

    @Autowired
    HistoricoMotoFilialRepository historicoRepository;

    @Autowired
    MotoRepository motoRepository;

    @Autowired
    MotoService motoService;

    @GetMapping("/historico")
    public ResponseEntity<List<MotoHistoricoDTO>> findAll() {
        var list = historicoRepository.findAll().stream().map(h ->
                new MotoHistoricoDTO(
                        h.getId(),
                        h.getMoto().getPlaca(),
                        h.getMoto().getModeloMoto().getModelo(),
                        h.getFilial().getNomeFilial(),
                        h.getDataMovimentacao()
                )
        ).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<HistoricoMotoEspecificaDTO> getHistoricoPorMoto(@PathVariable Long id) {
        getMoto(id);
        return ResponseEntity.ok(motoService.getHistoricoPorMoto(id));
    }

    private Moto getMoto(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
    }
}
