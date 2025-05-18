package br.com.fiap.mottu_api.repository;

import br.com.fiap.mottu_api.model.HistoricoMotoFilial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoMotoFilialRepository extends JpaRepository<HistoricoMotoFilial, Long> {
    List<HistoricoMotoFilial> findByMotoId(Long idMoto);
}
