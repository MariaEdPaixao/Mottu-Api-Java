package br.com.fiap.mottu_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.mottu_api.model.Moto;

public interface MotoRepository extends JpaRepository<Moto, Long>{
    
}
