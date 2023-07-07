package ru.net.pakhomov.farmers.api.repository;

import ru.net.pakhomov.farmers.api.model.farmer.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long>, JpaSpecificationExecutor<Farmer> {
    @Query("UPDATE Farmer f set f.archive = true where f.id = :id")
    @Modifying
    @Transactional
    void setArchiveTrue(Long id);

    boolean existsByInn(String inn);

}
