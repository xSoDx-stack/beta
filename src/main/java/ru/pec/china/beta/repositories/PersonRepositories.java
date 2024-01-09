package ru.pec.china.beta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pec.china.beta.entity.Person;

@Repository
public interface PersonRepositories extends JpaRepository<Person, Integer> {

}
