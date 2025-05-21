package br.com.fiap.mottu_api.repository;

import br.com.fiap.mottu_api.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FilialRepository extends JpaRepository<Filial, Long>, JpaSpecificationExecutor<Filial> {
}
