package br.com.fiap.mottu_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoMotoFilial {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_historico_moto_filial;

    @NotNull(message = "Campo obrigatório!")
    @ManyToOne
    private Moto moto;

    @NotNull(message = "Campo obrigatório!")
    @ManyToOne
    private Filial filial;
}
