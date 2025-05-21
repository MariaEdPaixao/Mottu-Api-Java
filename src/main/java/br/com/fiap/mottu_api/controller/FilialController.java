package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.repository.FilialRepository;
import br.com.fiap.mottu_api.specification.FilialSpecification;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/filial")
@Slf4j
public class FilialController {

    //todos os campos que serão possibilidades de filtro da filial
    public record FilialFiltros(String nomeFilial, String pais, String cidade, String estado){}

    @Autowired
    private FilialRepository filialRepository;

    @GetMapping
    public Page<Filial> index(
            FilialFiltros filtros,
            @PageableDefault(size = 4, sort = "nomeFilial", direction = Sort.Direction.ASC) Pageable pageable
            ){
        var specification = FilialSpecification.withFilters(filtros);
        return filialRepository.findAll(specification, pageable);
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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        log.info("Apagando uma filial " +id);

        filialRepository.delete(getFilial(id));
        return ResponseEntity.noContent().build(); //204
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable @Valid Long id, @RequestBody Filial filial){
        log.info("Atualizando filial + " + id);

        var oldFilial = getFilial(id);
        BeanUtils.copyProperties(filial, oldFilial, "id");
        filialRepository.save(oldFilial);
        return ResponseEntity.ok(oldFilial);
    }

    private Filial getFilial(Long id){
        return filialRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Filial não encontrada"));
    }
}
