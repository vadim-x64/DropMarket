package ua.project.dropmarket.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.dropmarket.entity.Product;

import java.io.IOException;

public interface ProductRepository extends JpaRepository<Product, Long> {

}