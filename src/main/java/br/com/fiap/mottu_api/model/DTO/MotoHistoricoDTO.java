package br.com.fiap.mottu_api.model.DTO;

import java.time.LocalDateTime;
// traz os dados essenciais do histórico de movimentação de motos nas filiais,
// um relatório reduzido
public record MotoHistoricoDTO(
        Long historicoId,
        String placaMoto,
        String modeloMoto,
        String nomeFilial,
        LocalDateTime dataMovimentacao
) {}
