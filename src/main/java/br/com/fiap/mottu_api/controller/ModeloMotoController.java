package br.com.fiap.mottu_api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.mottu_api.model.ModeloMoto;
import br.com.fiap.mottu_api.repository.ModeloMotoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/modeloMoto")
@Slf4j
public class ModeloMotoController {
    @Autowired ModeloMotoRepository modeloMotoRepository;

    @GetMapping
    @Cacheable("modeloMoto")
    public List<ModeloMoto> index(){
        return modeloMotoRepository.findAll();
    }

    @PostMapping
    @CacheEvict(value = "modeloMoto", allEntries = true) 
    @ResponseStatus(code = HttpStatus.CREATED)
    public ModeloMoto create(@RequestBody @Valid ModeloMoto moto){
        log.info("Cadastrando modelo da moto " + moto.getModelo());

        // Verifica se já existe um modelo com o mesmo nome
        modeloMotoRepository.findByModelo(moto.getModelo())
                .ifPresent(existing -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo já cadastrado.");
                });

        return modeloMotoRepository.save(moto);
    }


    @GetMapping("{id}")
    public ResponseEntity<ModeloMoto> get(@PathVariable Long id){
        log.info("Buscando um modelo de moto " +id);
        return ResponseEntity.ok(getModeloMoto(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "modeloMoto", allEntries = true) 
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid ModeloMoto modeloMoto){
        log.info("Atualizando modelo da moto " + id + " com " + modeloMoto.getId());

        modeloMotoRepository.findByModelo(modeloMoto.getModelo())
                .filter(m -> !m.getId().equals(id))
                .ifPresent(m -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo já cadastrado.");
                });

        var oldModeloMoto = getModeloMoto(id);
        BeanUtils.copyProperties(modeloMoto, oldModeloMoto, "id");
        modeloMotoRepository.save(oldModeloMoto);
        return ResponseEntity.ok(oldModeloMoto);
    }


    @DeleteMapping("{id}")
    @CacheEvict(value = "modeloMoto", allEntries = true) 
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        log.info("Apagando um modelo da moto " + id);

        modeloMotoRepository.delete(getModeloMoto(id));
        return ResponseEntity.noContent().build(); //204
    }


    private ModeloMoto getModeloMoto(Long id){
        return modeloMotoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Modelo não encontrado"));
    }
    
}
