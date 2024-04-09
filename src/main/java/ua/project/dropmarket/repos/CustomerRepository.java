package ua.project.dropmarket.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.dropmarket.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}