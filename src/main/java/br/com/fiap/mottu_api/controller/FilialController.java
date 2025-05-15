package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.repository.FilialRepository;
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
@RequestMapping("/filial")
@Slf4j
public class FilialController {

    @Autowired
    private FilialRepository filialRepository;

    @GetMapping
    public List<Filial> index(){
        return filialRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Filial create(@RequestBody @Valid Filial filial){
        log.info("Cadastrando filial" + filial.getId());
        return filialRepository.save(filial);
    }

    @GetMapping("{id}")
    public ResponseEntity<Filial> get(@PathVariable Long id){
        log.info("Buscando uma filial " +id);
        return ResponseEntity.ok(getFilial(id));
    }

    @DeleteMapping
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        log.info("Apagando uma filial " +id);

        filialRepository.delete(getFilial(id));
        return ResponseEntity.noContent().build(); //204
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable @Valid Long id, @RequestBody Filial filial){
        log.info("Atualizando moto + " + id);

        var oldFilial = getFilial(id);
        BeanUtils.copyProperties(filial, oldFilial, "id");
        filialRepository.save(oldFilial);
        return ResponseEntity.ok(oldFilial);
    }

    private Filial getFilial(Long id){
        return filialRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
    }
}
