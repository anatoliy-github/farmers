package ru.net.pakhomov.farmers.api.specification;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.net.pakhomov.farmers.api.model.district.District;
import java.util.Optional;

@Component
@NoArgsConstructor
public class DistrictSpecification {
    public static Specification<District> booleanEquals(String key, Optional<Boolean> value) {
        return (root, query, criteriaBuilder) -> value
                .map(val -> criteriaBuilder.equal(root.get(key), val))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<District> stringContains(String key, Optional<String> value) {
        return (root, query, criteriaBuilder) -> value
                .map(val -> criteriaBuilder.like(criteriaBuilder.lower(root.get(key)), "%" + val.toLowerCase() + "%"))
                .orElseGet(criteriaBuilder::conjunction);
    }
    public static Specification<District> filtersQuery(Optional<Boolean> archive, Optional<String> name, Optional<String> code) {
        return Specification
                .where(DistrictSpecification.booleanEquals("archive", archive))
                .and(DistrictSpecification.stringContains("name", name))
                .and(DistrictSpecification.stringContains("code", code));
    }
}
