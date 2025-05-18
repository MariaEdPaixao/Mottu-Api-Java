package br.com.fiap.mottu_api.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class MovimentacaoDTO {

    private String nomeFilial;
    private LocalDateTime dataMovimentacao;

    public MovimentacaoDTO(String nomeFilial, LocalDateTime dataMovimentacao) {
        this.nomeFilial = nomeFilial;
        this.dataMovimentacao = dataMovimentacao;
    }
}
