package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.model.HistoricoFilialDTO;
import br.com.fiap.mottu_api.repository.FilialRepository;
import br.com.fiap.mottu_api.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/filial")
public class HistoricoFilialController {

    @Autowired
    private FilialService filialService;

    @Autowired
    private FilialRepository filialRepository;

    @GetMapping("/{id}/historico")
    public ResponseEntity<HistoricoFilialDTO> getHistoricoPorFilial(@PathVariable Long id) {
        Filial filial = filialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filial não encontrada"));

        return ResponseEntity.ok(filialService.getHistoricoPorFilial(id));
    }
}
