package ua.project.dropmarket.service;

import org.springframework.stereotype.Service;
import ua.project.dropmarket.entity.Product;
import ua.project.dropmarket.repos.ProductRepository;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }
}