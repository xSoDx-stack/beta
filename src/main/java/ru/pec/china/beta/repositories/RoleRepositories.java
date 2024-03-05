package ru.pec.china.beta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pec.china.beta.entity.Role;

public interface RoleRepositories extends JpaRepository<Role, String> {
}
