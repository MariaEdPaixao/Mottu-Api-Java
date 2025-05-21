package br.com.fiap.mottu_api.model;

import jakarta.persistence.*;
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
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da filial não pode estar em branco.")
    @Size(min = 6)
    private String nomeFilial;

    @NotBlank(message = "O logradouro é obrigatório.")
    private String logradouro;

    private int numero;

    private String complemento;

    @NotBlank(message = "O CEP é obrigatório.")
    private String cep;

    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório.")
    private String estado;

    @NotBlank(message = "O país é obrigatório.")
    private String pais;
}
