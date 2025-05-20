package br.com.fiap.mottu_api.controller;

import java.util.List;
import java.util.Optional;

import br.com.fiap.mottu_api.model.ModeloMoto;
import br.com.fiap.mottu_api.repository.ModeloMotoRepository;
import br.com.fiap.mottu_api.service.MotoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.fiap.mottu_api.model.Moto;
import br.com.fiap.mottu_api.repository.MotoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/moto")
@Slf4j
public class MotoController {
    
    @Autowired MotoRepository motoRepository;
    @Autowired
    ModeloMotoRepository modeloMotoRepository;

    @Autowired
    MotoService motoService = new MotoService();

    @GetMapping
    public List<Moto> index(){
        return motoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Moto create(@RequestBody @Valid Moto moto) {
        motoService.validarMoto(moto, null); // null porque ainda não existe ID
        log.info("Cadastrando moto " + moto.getPlaca());
        return motoRepository.save(moto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Moto> get(@PathVariable Long id){
        log.info("Buscando uma moto " +id);
        return ResponseEntity.ok(getMoto(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid Moto moto) {
        log.info("Atualizando moto " + id + " com " + moto.getId());

        Moto motoExistente = getMoto(id);
        motoService.validarMoto(moto, id);

        BeanUtils.copyProperties(moto, motoExistente, "id");
        motoRepository.save(motoExistente);
        return ResponseEntity.ok(motoExistente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        log.info("Apagando uma moto " + id);

        motoRepository.delete(getMoto(id));
        return ResponseEntity.noContent().build(); //204
    }


    private Moto getMoto(Long id){
        return motoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
    }
}
