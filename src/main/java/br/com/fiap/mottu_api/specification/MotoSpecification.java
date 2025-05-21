package br.com.fiap.mottu_api.specification;

import br.com.fiap.mottu_api.controller.MotoController.MotosFiltros;
import br.com.fiap.mottu_api.model.Moto;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;


import java.util.ArrayList;

public class MotoSpecification {
    public static Specification<Moto> withFilters(MotosFiltros filtros){
        return (root, query, cb) -> {
            var predicates = new ArrayList<>();

            if (filtros.placa() != null){
                predicates.add(
                        cb.like(cb.lower(root.get("placa")), "%" + filtros.placa().toLowerCase() + "%")
                );
            }
            if (filtros.modelo() != null && !filtros.modelo().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("modeloMoto").get("modelo")), "%" + filtros.modelo().toLowerCase() + "%"));
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };
    }
}
