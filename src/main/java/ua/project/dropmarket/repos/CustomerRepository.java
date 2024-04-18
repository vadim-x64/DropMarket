package ua.project.dropmarket.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.entity.Users;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUserUsername(String username);
    Customer findByUser(Users user);
}