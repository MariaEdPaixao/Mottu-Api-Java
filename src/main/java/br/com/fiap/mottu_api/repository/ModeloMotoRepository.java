package br.com.fiap.mottu_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.mottu_api.model.ModeloMoto;


public interface ModeloMotoRepository extends JpaRepository<ModeloMoto, Long>{
    
}
