package br.com.fiap.mottu_api.model.DTO;

import java.util.List;

public record HistoricoFilialDTO(String nomeFilial, List<MotoNaFilialDTO> motos) {}

