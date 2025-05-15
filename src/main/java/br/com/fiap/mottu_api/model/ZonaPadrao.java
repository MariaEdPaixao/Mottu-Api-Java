package br.com.fiap.mottu_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZonaPadrao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da zona padrão não pode estar em branco.")
    @Size( min = 6)
    private String nomeZona;

    @NotBlank(message = "A descrição da zona padrão não pode estar em branco.")
    @Size( min = 6)
    private String descricao;

    @NotBlank(message = "A cor não pode estar em branco.")
    @Size( min = 3)
    private String cor_zona;
}
