package ru.net.pakhomov.farmers.api.specification;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.net.pakhomov.farmers.api.model.farmer.Farmer;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Component
@NoArgsConstructor
public class FarmerSpecification {
    public static Specification<Farmer> booleanEquals(String key, Optional<Boolean> value) {
        return (root, query, criteriaBuilder) -> value
                .map(val -> criteriaBuilder.equal(root.get(key), val))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Farmer> longEquals(String key, Optional<Long> value) {
        return (root, query, criteriaBuilder) -> value
                .map(val -> criteriaBuilder.equal(root.get(key), val))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Farmer> stringContains(String key, Optional<String> value) {
        return (root, query, criteriaBuilder) -> value
                .map(val -> criteriaBuilder.like(criteriaBuilder.lower(root.get(key)), "%" + val.toLowerCase() + "%"))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Farmer> dateEquals(String key, Optional<Date> date) {
        return (root, query, criteriaBuilder) -> date
                .map(d -> criteriaBuilder.lessThanOrEqualTo(root.get(key), d))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Farmer> filtersQuery(
            Optional<String> name,
            Optional<String> inn,
            Optional<Long> district,
            Optional<Date> date,
            Optional<Boolean> archive) {
        return Specification
                .where(FarmerSpecification.stringContains("name", name))
                .and(FarmerSpecification.stringContains("inn", inn))
                .and(FarmerSpecification.longEquals("district_id", district))
                .and(FarmerSpecification.dateEquals("registration_date", date))
                .and(FarmerSpecification.booleanEquals("archive", archive));
    }
}
