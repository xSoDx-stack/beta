package ru.pec.china.beta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.pec.china.beta.entity.Role;
import ru.pec.china.beta.repositories.RoleRepositories;

@SpringBootApplication
@EnableJpaRepositories

public class ChinaProjectPecApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChinaProjectPecApplication.class, args);

    }

    @Bean
    public CommandLineRunner initDatabase(RoleRepositories roleRepository) {
        return args -> {
            if (roleRepository.findById("ADMIN").isEmpty()) {
                Role role = new Role();
                role.setId("ADMIN");
                role.setName("Администратор");
                roleRepository.save(role);
            }
            if (roleRepository.findById("OPERATOR").isEmpty()) {
                Role role = new Role();
                role.setId("OPERATOR");
                role.setName("Оператор");
                roleRepository.save(role);
            }
            if (roleRepository.findById("SPECIALIST").isEmpty()) {
                Role role = new Role();
                role.setId("SPECIALIST");
                role.setName("Специалист");
                roleRepository.save(role);
            }
        };
    }
}
