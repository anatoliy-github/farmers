package ru.net.pakhomov.farmers.api.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.net.pakhomov.farmers.api.model.district.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {
    @Query("update District d set d.archive = true where d.id = :id")
    @Modifying
    @Transactional
    void setArchiveTrue(Long id);

    boolean existsByCode(String code);

    boolean existsByName(String name);

    @Query("select d from District d where d.name = :name")
    Optional<District> findByNameContaining(String name);
}
