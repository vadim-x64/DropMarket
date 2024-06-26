package ua.project.dropmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.dropmarket.entity.Product;
import ua.project.dropmarket.entity.Users;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCreatedBy(Users user);
}