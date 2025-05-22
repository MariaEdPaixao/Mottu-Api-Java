package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.ModeloMoto;
import br.com.fiap.mottu_api.repository.ModeloMotoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/modeloMoto")
@Slf4j
@Tag(name = "Modelo de Moto", description = "Gerenciamento dos modelos de moto cadastrados")
public class ModeloMotoController {

    @Autowired ModeloMotoRepository modeloMotoRepository;

    @GetMapping
    @Cacheable("modeloMoto")
    @Operation(summary = "Listar modelos de moto", description = "Retorna todos os modelos de moto cadastrados")
    public List<ModeloMoto> index() {
        return modeloMotoRepository.findAll();
    }

    @PostMapping
    @CacheEvict(value = "modeloMoto", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar modelo de moto", description = "Cria um novo modelo de moto.")
    public ModeloMoto create(@RequestBody @Valid ModeloMoto moto) {
        modeloMotoRepository.findByModelo(moto.getModelo())
                .ifPresent(existing -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo já cadastrado.");
                });
        return modeloMotoRepository.save(moto);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar modelo por ID", description = "Retorna os dados de um modelo específico.")
    public ResponseEntity<ModeloMoto> get(@PathVariable Long id) {
        return ResponseEntity.ok(getModeloMoto(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "modeloMoto", allEntries = true)
    @Operation(summary = "Atualizar modelo de moto", description = "Atualiza os dados de um modelo existente.")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid ModeloMoto modeloMoto) {
        modeloMotoRepository.findByModelo(modeloMoto.getModelo())
                .filter(m -> !m.getId().equals(id))
                .ifPresent(m -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo já cadastrado.");
                });
        var oldModeloMoto = getModeloMoto(id);
        BeanUtils.copyProperties(modeloMoto, oldModeloMoto, "id");
        return ResponseEntity.ok(modeloMotoRepository.save(oldModeloMoto));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "modeloMoto", allEntries = true)
    @Operation(summary = "Excluir modelo de moto", description = "Remove um modelo de moto do sistema pelo ID.")
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        modeloMotoRepository.delete(getModeloMoto(id));
        return ResponseEntity.noContent().build();
    }

    private ModeloMoto getModeloMoto(Long id) {
        return modeloMotoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Modelo não encontrado"));
    }
}