package br.com.fiap.mottu_api.model.DTO;


import java.time.LocalDateTime;

public record MotoNaFilialDTO(String placa, String modelo, LocalDateTime dataMovimentacao) {}
