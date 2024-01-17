package ru.pec.china.beta.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pec.china.beta.entity.Truck;

@Repository
public interface TruckRepositories extends JpaRepository<Truck, Integer> {

    Page<Truck> findAllByOrderByDateCreateDesc(Pageable page);
}
