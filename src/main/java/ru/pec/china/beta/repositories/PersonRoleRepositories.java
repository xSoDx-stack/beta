package ru.pec.china.beta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pec.china.beta.entity.PersonRole;

@Repository
public interface PersonRoleRepositories extends JpaRepository<PersonRole, Integer> {

    void deleteByPersonId(Integer personId);

}
