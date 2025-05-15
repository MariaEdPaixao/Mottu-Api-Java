package br.com.fiap.mottu_api.repository;

import br.com.fiap.mottu_api.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilialRepository extends JpaRepository<Filial, Long> {
}
