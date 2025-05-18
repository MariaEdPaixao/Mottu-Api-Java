package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.HistoricoMotoEspecificaDTO;
import br.com.fiap.mottu_api.model.MotoHistoricoDTO;
import br.com.fiap.mottu_api.repository.HistoricoMotoFilialRepository;
import br.com.fiap.mottu_api.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico-dto")
public class HistoricoDTOController {

    @Autowired
    HistoricoMotoFilialRepository repository;

    @Autowired
    MotoService motoService;

    @GetMapping
    public ResponseEntity<List<MotoHistoricoDTO>> findAll() {
        var list = repository.findAll().stream().map(h ->
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

    @GetMapping("/moto/{id}/historico")
    public ResponseEntity<HistoricoMotoEspecificaDTO> getHistoricoPorMoto(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.getHistoricoPorMoto(id));
    }

}
