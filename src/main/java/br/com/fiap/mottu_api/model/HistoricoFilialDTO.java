package br.com.fiap.mottu_api.model;

import java.util.List;

public record HistoricoFilialDTO(String nomeFilial, List<MotoNaFilialDTO> motos) {}

