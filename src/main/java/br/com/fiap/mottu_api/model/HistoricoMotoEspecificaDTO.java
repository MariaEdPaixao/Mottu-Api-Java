package br.com.fiap.mottu_api.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class HistoricoMotoEspecificaDTO {

    private String placa;
    private String modelo;

    private List<MovimentacaoDTO> movimentacoes;

    public HistoricoMotoEspecificaDTO(String placa, String modelo, List<MovimentacaoDTO> movimentacoes) {
        this.placa = placa;
        this.modelo = modelo;
        this.movimentacoes = movimentacoes;
    }

}

