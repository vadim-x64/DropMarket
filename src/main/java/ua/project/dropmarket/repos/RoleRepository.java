package ua.project.dropmarket.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.dropmarket.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}