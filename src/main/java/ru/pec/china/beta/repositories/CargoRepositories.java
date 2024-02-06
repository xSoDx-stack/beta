package ru.pec.china.beta.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pec.china.beta.entity.Cargo;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CargoRepositories extends JpaRepository<Cargo, UUID> {
    Page<Cargo> findAllByTruckId(Integer id, Pageable pageable);

    Page<Cargo> findAllByProcessedTrueAndIssuanceFalse(Pageable pageable);

    Page<Cargo> findAllByProcessedFalse(Pageable pageable);

    Page<Cargo> findAllByIssuanceTrue(Pageable pageable);

    @Query("select c from Cargo c where c.clientBarcode like %:search% or c.pecCode ilike %:search%")
    Page<Cargo> searchCode(@Param("search") String search, Pageable pageable);

    @Query("select c from Cargo c where c.clientBarcode = :search")
    Optional<Cargo> searchCargoByPecCode(@Param("search") String search);
}
