package br.com.fiap.mottu_api.config;

import br.com.fiap.mottu_api.model.*;
import br.com.fiap.mottu_api.repository.FilialRepository;
import br.com.fiap.mottu_api.repository.HistoricoMotoFilialRepository;
import br.com.fiap.mottu_api.repository.ModeloMotoRepository;
import br.com.fiap.mottu_api.repository.MotoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder {

    @Autowired
    private ModeloMotoRepository modeloMotoRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    private HistoricoMotoFilialRepository historicoRepository;

    @PostConstruct
    public void init() {
        var modelos = List.of(
                ModeloMoto.builder().modelo("Mottu E").build(),
                ModeloMoto.builder().modelo("Mottu Sport").build(),
                ModeloMoto.builder().modelo("Mottu Pop").build()
        );
        modeloMotoRepository.saveAll(modelos);

        var filiais = List.of(
                Filial.builder().nomeFilial("Mottu Butantã").logradouro("Rua Agostinho Cantu").numero(209).complemento("").cep("05501010").cidade("São Paulo").estado("SP").pais("Brasil").build(),
                Filial.builder().nomeFilial("Mottu Copacabana").logradouro("Av. Brasil").numero(200).complemento("").cep("22290070").cidade("Rio de Janeiro").estado("RJ").pais("Brasil").build(),
                Filial.builder().nomeFilial("Mottu Belo Horizonte").logradouro("R. José Benedito Antão").numero(200).complemento("").cep("31250115").cidade("Belo Horizonte").estado("MG").pais("Brasil").build(),
                Filial.builder().nomeFilial("Mottu CDMX").logradouro("Laguna del Carmen").numero(115).complemento("Entre Lago Chapala e Lago Cuitzeo").cep("11320").cidade("Cidade do México").estado("CDMX").pais("México").build()
        );
        filialRepository.saveAll(filiais);

        var placas = List.of("ABC1A23", "DEF4B56", "GHI7C89", "JKL0D01", "MNO2E34", "123 4A", "901 2C");
        List<Moto> motos = new ArrayList<>();
        Random random = new Random();

        for (String placa : placas) {
            Moto moto = Moto.builder()
                    .placa(placa)
                    .modeloMoto(modelos.get(random.nextInt(modelos.size())))
                    .build();
            motos.add(moto);
        }
        motoRepository.saveAll(motos);

        List<HistoricoMotoFilial> historicos = new ArrayList<>();
        for (Moto moto : motos) {
            for (int i = 0; i < 2; i++) {
                Filial filial = filiais.get(random.nextInt(filiais.size()));
                LocalDateTime dataAleatoria = LocalDateTime.now().minusDays(random.nextInt(10));
                historicos.add(
                        HistoricoMotoFilial.builder()
                                .moto(moto)
                                .filial(filial)
                                .dataMovimentacao(dataAleatoria)
                                .build()
                );
            }
        }
        historicoRepository.saveAll(historicos);
    }
}
