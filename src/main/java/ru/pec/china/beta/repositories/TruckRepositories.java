package ru.pec.china.beta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Truck;

import java.util.List;

@Repository
public interface TruckRepositories extends JpaRepository<Truck, Integer> {

}
