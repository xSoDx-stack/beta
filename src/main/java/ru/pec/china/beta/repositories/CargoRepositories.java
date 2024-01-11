package ru.pec.china.beta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pec.china.beta.entity.Cargo;

import java.util.List;
import java.util.UUID;

@Repository
public interface CargoRepositories extends JpaRepository<Cargo, UUID> {
    List<Cargo> findAllByTruckId(Integer id);

    List<Cargo> findAllByProcessedTrueAndIssuanceFalse();

    List<Cargo> findAllByProcessedFalse();
}
