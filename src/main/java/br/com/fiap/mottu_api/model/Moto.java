package br.com.fiap.mottu_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Moto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A placa não pode estar em branco")
    @Size(max = 7, min = 6)
    @Column(unique = true)
    private String placa;

    @NotNull(message = "Campo obrigatório!")
    @ManyToOne
    private ModeloMoto modeloMoto;

    @OneToMany(mappedBy = "moto")
    @JsonIgnore
    private List<HistoricoMotoFilial> historicoFiliais = new ArrayList<>();

}
