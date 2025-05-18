package br.com.fiap.mottu_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.mottu_api.model.Moto;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long>{
    boolean existsByPlaca(String placa);

    Optional<Moto> findByPlaca(String placa);
}
