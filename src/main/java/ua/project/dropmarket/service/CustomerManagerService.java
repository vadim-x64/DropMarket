package ua.project.dropmarket.service;

import org.springframework.stereotype.Service;
import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.entity.Product;
import ua.project.dropmarket.repos.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerManagerService {
    private final CustomerRepository customerRepository;

    public CustomerManagerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomerToDB(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUserUsername(username);
    }

    public void updateCustomer(Long customerId, String firstName, String lastName, String email, String phone, String age, String address) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setAge(age);
            customer.setAddress(address);
            customerRepository.save(customer);
        }
    }
}