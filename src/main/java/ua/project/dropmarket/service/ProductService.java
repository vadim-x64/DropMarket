package ua.project.dropmarket.service;

import org.springframework.stereotype.Service;
import ua.project.dropmarket.entity.Product;
import ua.project.dropmarket.entity.Users;
import ua.project.dropmarket.repos.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByCreatedBy(Users user) {
        return productRepository.findByCreatedBy(user);
    }

    public Product saveDate(Product product) {
        product.setCreatedAt(LocalDateTime.now()); // Встановіть поточну дату і час перед збереженням
        return productRepository.save(product);
    }

    public void updateProduct(Long productId, String name, String description, String producer, BigDecimal price, boolean available, String photoUrl) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(name);
            product.setDescription(description);
            product.setProducer(producer);
            product.setPrice(price);
            product.setAvailable(available);
            product.setPhoto(photoUrl); // Set new photo URL
            productRepository.save(product);
        }
    }
}