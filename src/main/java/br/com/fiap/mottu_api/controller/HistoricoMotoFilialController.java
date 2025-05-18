package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.HistoricoMotoFilial;
import br.com.fiap.mottu_api.repository.HistoricoMotoFilialRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/historico")
@Slf4j
public class HistoricoMotoFilialController {

    @Autowired
    HistoricoMotoFilialRepository historicoRepository;

    @GetMapping
    public List<HistoricoMotoFilial> index() {
        return historicoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HistoricoMotoFilial create(@RequestBody @Valid HistoricoMotoFilial historico) {
        log.info("Cadastrando histórico moto-filial: " + historico);
        return historicoRepository.save(historico);
    }

    @GetMapping("{id}")
    public ResponseEntity<HistoricoMotoFilial> get(@PathVariable Long id) {
        log.info("Buscando histórico por ID " + id);
        return ResponseEntity.ok(getHistorico(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid HistoricoMotoFilial historico) {
        log.info("Atualizando histórico " + id);
        var oldHistorico = getHistorico(id);
        BeanUtils.copyProperties(historico, oldHistorico, "id");
        historicoRepository.save(oldHistorico);
        return ResponseEntity.ok(oldHistorico);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Deletando histórico " + id);
        historicoRepository.delete(getHistorico(id));
        return ResponseEntity.noContent().build();
    }

    private HistoricoMotoFilial getHistorico(Long id) {
        return historicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado"));
    }
}
