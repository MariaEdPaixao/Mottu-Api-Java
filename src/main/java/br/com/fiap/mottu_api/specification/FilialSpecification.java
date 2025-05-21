package br.com.fiap.mottu_api.specification;

import br.com.fiap.mottu_api.controller.FilialController.FilialFiltros;
import br.com.fiap.mottu_api.model.Filial;
import br.com.fiap.mottu_api.model.Moto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class FilialSpecification {
    public static Specification<Filial> withFilters(FilialFiltros filtros){
        return (root, query, cb) -> {
            var predicates = new ArrayList<>();

            if (filtros.nomeFilial() != null){
                predicates.add(
                        cb.like(cb.lower(root.get("nomeFilial")), "%" + filtros.nomeFilial().toLowerCase() + "%")
                );
            }
            if (filtros.pais() != null){
                predicates.add(
                        cb.like(cb.lower(root.get("pais")), "%" + filtros.pais().toLowerCase() + "%")
                );
            }
            if (filtros.estado() != null){
                predicates.add(
                        cb.like(cb.lower(root.get("estado")), "%" + filtros.estado().toLowerCase() + "%")
                );
            }
            if (filtros.cidade() != null){
                predicates.add(
                        cb.like(cb.lower(root.get("cidade")), "%" + filtros.cidade().toLowerCase() + "%")
                );
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };
    }
}
