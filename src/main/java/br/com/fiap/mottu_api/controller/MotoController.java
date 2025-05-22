package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.Moto;
import br.com.fiap.mottu_api.repository.ModeloMotoRepository;
import br.com.fiap.mottu_api.repository.MotoRepository;
import br.com.fiap.mottu_api.service.MotoService;
import br.com.fiap.mottu_api.specification.MotoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
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
@RequestMapping("/moto")
@Slf4j
@Tag(name = "Moto", description = "Endpoints para gerenciamento de motos")
public class MotoController {

    public record MotosFiltros(String placa, String modelo) {}

    @Autowired MotoRepository motoRepository;
    @Autowired ModeloMotoRepository modeloMotoRepository;
    @Autowired MotoService motoService;

    @GetMapping
    @Cacheable("motos")
    @Operation(summary = "Listar motos", description = "Retorna uma lista paginada de motos filtradas por placa e modelo.")
    public Page<Moto> index(
            @ParameterObject MotosFiltros filtros,
            @ParameterObject @PageableDefault(size = 4, sort = "placa", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var specification = MotoSpecification.withFilters(filtros);
        return motoRepository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "motos", allEntries = true)
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Cadastrar moto", description = "Cria uma nova moto com os dados informados.")
    public Moto create(@RequestBody @Valid Moto moto) {
        motoService.validarMoto(moto, null);
        log.info("Cadastrando moto " + moto.getPlaca());
        return motoRepository.save(moto);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar moto por ID", description = "Retorna os dados de uma moto específica pelo ID.")
    public ResponseEntity<Moto> get(@PathVariable Long id) {
        return ResponseEntity.ok(getMoto(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "motos", allEntries = true)
    @Operation(summary = "Atualizar moto", description = "Atualiza os dados de uma moto específica.")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid Moto moto) {
        Moto motoExistente = getMoto(id);
        motoService.validarMoto(moto, id);
        BeanUtils.copyProperties(moto, motoExistente, "id");
        return ResponseEntity.ok(motoRepository.save(motoExistente));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "motos", allEntries = true)
    @Operation(summary = "Excluir moto", description = "Exclui uma moto do sistema pelo ID.")
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        motoRepository.delete(getMoto(id));
        return ResponseEntity.noContent().build();
    }

    private Moto getMoto(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
    }
}
