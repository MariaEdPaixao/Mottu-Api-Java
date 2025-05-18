package br.com.fiap.mottu_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.fiap.mottu_api.model.HistoricoMotoFilial;
import java.util.List;
import java.util.ArrayList;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filial {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da filial não pode estar em branco.")
    @Size( min = 6)
    private String nomeFilial;

    @OneToMany(mappedBy = "filial")
    @JsonIgnore
    private List<HistoricoMotoFilial> historicoMotos = new ArrayList<>();
}
