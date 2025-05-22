package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.model.HistoricoMotoFilial;
import br.com.fiap.mottu_api.model.Moto;
import br.com.fiap.mottu_api.repository.FilialRepository;
import br.com.fiap.mottu_api.repository.HistoricoMotoFilialRepository;
import br.com.fiap.mottu_api.repository.MotoRepository;
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
@RequestMapping("/historico")
@Slf4j
@Tag(name = "Histórico Moto-Filial", description = "Operações relacionadas ao histórico de movimentações entre motos e filiais.")
public class HistoricoMotoFilialController {

    @Autowired
    HistoricoMotoFilialRepository historicoRepository;

    @Autowired
    MotoRepository motoRepository;

    @Autowired
    FilialRepository filialRepository;

    @GetMapping
    @Cacheable("historicoMotoFilial")
    @Operation(
            summary = "Listar histórico de movimentações",
            description = "Retorna uma lista paginada de registros do histórico de movimentações entre motos e filiais.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de históricos retornada com sucesso")
            }
    )
    public Page<HistoricoMotoFilial> index(
            @ParameterObject  @PageableDefault(size = 4, sort = "dataMovimentacao", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return historicoRepository.findAll(pageable);
    }

    @PostMapping
    @CacheEvict(value = "historicoMotoFilial", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar movimentação",
            description = "Registra uma nova movimentação de moto entre filiais.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Movimentação registrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public HistoricoMotoFilial create(@RequestBody @Valid HistoricoMotoFilial historico) {
        log.info("Cadastrando histórico moto-filial: " + historico);
        getMoto(historico.getMoto().getId());
        getFilial(historico.getFilial().getId());
        return historicoRepository.save(historico);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Buscar movimentação por ID",
            description = "Retorna os dados de uma movimentação específica com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Histórico encontrado"),
                    @ApiResponse(responseCode = "404", description = "Histórico não encontrado")
            }
    )
    public ResponseEntity<HistoricoMotoFilial> get(@PathVariable Long id) {
        log.info("Buscando histórico por ID " + id);
        return ResponseEntity.ok(getHistorico(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "historicoMotoFilial", allEntries = true)
    @Operation(
            summary = "Atualizar movimentação",
            description = "Atualiza os dados de uma movimentação de moto-filial existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Histórico atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Histórico não encontrado")
            }
    )
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
    @Operation(
            summary = "Deletar movimentação",
            description = "Remove um registro de movimentação do sistema pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Histórico removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Histórico não encontrado")
            }
    )
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
