package ua.project.dropmarket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.project.dropmarket.entity.Role;
import ua.project.dropmarket.repos.RoleRepository;

@SpringBootApplication
public class DropMarketApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DropMarketApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository)
    {
        return args ->
        {
            if (roleRepository.findAll().isEmpty())
            {
                roleRepository.save(new Role(1L, "ROLE_User"));
            }
        };
    }
}