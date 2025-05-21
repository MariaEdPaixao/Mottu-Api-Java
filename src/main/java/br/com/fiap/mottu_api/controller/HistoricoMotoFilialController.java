package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.model.HistoricoMotoFilial;
import br.com.fiap.mottu_api.model.Moto;
import br.com.fiap.mottu_api.repository.FilialRepository;
import br.com.fiap.mottu_api.repository.HistoricoMotoFilialRepository;
import br.com.fiap.mottu_api.repository.MotoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/historico")
@Slf4j
public class HistoricoMotoFilialController {

    @Autowired
    HistoricoMotoFilialRepository historicoRepository;

    @Autowired
    MotoRepository motoRepository;

    @Autowired
    FilialRepository filialRepository;


    @GetMapping
    @Cacheable("historicoMotoFilial")
    public Page<HistoricoMotoFilial> index(
            @PageableDefault(size = 4, sort = "dataMovimentacao", direction = Sort.Direction.DESC)Pageable pageable
            ) {
        return historicoRepository.findAll(pageable);
    }

    @PostMapping
    @CacheEvict(value = "historicoMotoFilial", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    public HistoricoMotoFilial create(@RequestBody @Valid HistoricoMotoFilial historico) {
        log.info("Cadastrando histórico moto-filial: " + historico);
        getMoto(historico.getMoto().getId());
        getFilial(historico.getFilial().getId());
        return historicoRepository.save(historico);
    }

    @GetMapping("{id}")
    public ResponseEntity<HistoricoMotoFilial> get(@PathVariable Long id) {
        log.info("Buscando histórico por ID " + id);
        return ResponseEntity.ok(getHistorico(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "historicoMotoFilial", allEntries = true)
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid HistoricoMotoFilial historico) {
        log.info("Atualizando histórico " + id);
        var oldHistorico = getHistorico(id);
        BeanUtils.copyProperties(historico, oldHistorico, "id");
        getMoto(historico.getMoto().getId());
        getFilial(historico.getFilial().getId());
        historicoRepository.save(oldHistorico);
        return ResponseEntity.ok(oldHistorico);
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "historicoMotoFilial", allEntries = true)
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Deletando histórico " + id);
        historicoRepository.delete(getHistorico(id));
        return ResponseEntity.noContent().build();
    }

    private HistoricoMotoFilial getHistorico(Long id) {
        return historicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado"));
    }

    private Moto getMoto(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
    }
    private Filial getFilial(Long id) {
        return filialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filial não encontrada"));
    }
}
