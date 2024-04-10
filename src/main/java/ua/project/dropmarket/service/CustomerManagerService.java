package ua.project.dropmarket.service;

import org.springframework.stereotype.Service;
import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.entity.Users;
import ua.project.dropmarket.repos.CustomerRepository;

@Service
public class CustomerManagerService {
    private final CustomerRepository customerRepository;

    public CustomerManagerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomerToDB(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getCustomerByUser(Users user) {
        return customerRepository.findByUser(user);
    }
}