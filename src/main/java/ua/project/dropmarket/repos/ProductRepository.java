package ua.project.dropmarket.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.dropmarket.entity.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();
}