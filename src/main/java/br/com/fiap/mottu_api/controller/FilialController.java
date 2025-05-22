package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.repository.FilialRepository;
import br.com.fiap.mottu_api.specification.FilialSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/filial")
@Slf4j
@Tag(name = "Filial", description = "Operações relacionadas às filiais")
public class FilialController {

    public record FilialFiltros(String nomeFilial, String pais, String cidade, String estado) {}

    @Autowired
    private FilialRepository filialRepository;

    @GetMapping
    @Cacheable("filial")
    @Operation(
            summary = "Listar todas as filiais",
            description = "Retorna uma lista paginada com as filiais filtradas por nome, país, cidade ou estado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    public Page<Filial> index(
            @ParameterObject FilialFiltros filtros,
            @ParameterObject @PageableDefault(size = 4, sort = "nomeFilial", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var specification = FilialSpecification.withFilters(filtros);
        return filialRepository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "filial", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar nova filial",
            description = "Cria uma nova filial com os dados informados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Filial criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public Filial create(@RequestBody @Valid Filial filial) {
        log.info("Cadastrando filial " + filial.getId());
        return filialRepository.save(filial);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Buscar filial por ID",
            description = "Retorna os dados de uma filial específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filial encontrada"),
                    @ApiResponse(responseCode = "404", description = "Filial não encontrada")
            }
    )
    public ResponseEntity<Filial> get(@PathVariable Long id) {
        log.info("Buscando uma filial " + id);
        return ResponseEntity.ok(getFilial(id));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "filial", allEntries = true)
    @Operation(
            summary = "Excluir filial",
            description = "Remove uma filial do sistema pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Filial excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Filial não encontrada")
            }
    )
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("Apagando uma filial " + id);
        filialRepository.delete(getFilial(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @CacheEvict(value = "filial", allEntries = true)
    @Operation(
            summary = "Atualizar filial",
            description = "Atualiza os dados de uma filial existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filial atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Filial não encontrada")
            }
    )
    public ResponseEntity<Object> update(@PathVariable @Valid Long id, @RequestBody Filial filial) {
        log.info("Atualizando filial + " + id);
        var oldFilial = getFilial(id);
        BeanUtils.copyProperties(filial, oldFilial, "id");
        filialRepository.save(oldFilial);
        return ResponseEntity.ok(oldFilial);
    }

    private Filial getFilial(Long id) {
        return filialRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Filial não encontrada"));
    }
}
