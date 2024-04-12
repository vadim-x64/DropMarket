package ua.project.dropmarket.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.dropmarket.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}