package ua.project.dropmarket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.project.dropmarket.entity.Role;
import ua.project.dropmarket.repository.RoleRepository;

@SpringBootApplication
public class DropMarket {
    public static void main(String[] args) {
        SpringApplication.run(DropMarket.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findAll().isEmpty()) {
                roleRepository.save(new Role(1L, "ROLE_User"));
            }
        };
    }
}