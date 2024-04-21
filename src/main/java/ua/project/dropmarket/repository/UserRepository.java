package ua.project.dropmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.dropmarket.entity.Users;
import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findAllByUsername(String username);
    Users findByUsername(String username);
}