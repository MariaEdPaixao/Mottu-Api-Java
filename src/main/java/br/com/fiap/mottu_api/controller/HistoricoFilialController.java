package br.com.fiap.mottu_api.controller;

import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.model.DTO.HistoricoFilialDTO;
import br.com.fiap.mottu_api.repository.FilialRepository;
import br.com.fiap.mottu_api.service.FilialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/filial")
@Tag(name = "Histórico da Filial", description = "Endpoints para visualizar o histórico de movimentações de motos por filial.")
public class HistoricoFilialController {

    @Autowired
    private FilialService filialService;

    @Autowired
    private FilialRepository filialRepository;

    @GetMapping("/{id}/historico")
    @Operation(
            summary = "Buscar histórico de uma filial",
            description = "Retorna todos os registros de movimentação de motos que passaram por uma filial específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Filial não encontrada")
            }
    )
    public ResponseEntity<HistoricoFilialDTO> getHistoricoPorFilial(@PathVariable Long id) {
        Filial filial = filialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filial não encontrada"));

        return ResponseEntity.ok(filialService.getHistoricoPorFilial(id));
    }
}
