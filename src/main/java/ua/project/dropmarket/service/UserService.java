package ua.project.dropmarket.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.entity.Role;
import ua.project.dropmarket.entity.Users;
import ua.project.dropmarket.repository.CustomerRepository;
import ua.project.dropmarket.repository.UserRepository;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Getter
    private final CustomerRepository customerRepository;

    @Autowired
    public UserService(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public boolean getLogicByUser(String username) {
        return !userRepository.findAllByUsername(username).isEmpty();
    }

    public Users saveNewUserToDB(Users user) {
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_User")));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("Користувача не знайдено!");
        }
        return user;
    }

    public void deleteUserByUsername(String username) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            Customer customer = customerRepository.findByUser(user);
            if (customer != null) {
                customerRepository.delete(customer);
            }
            userRepository.delete(user);
        }
    }
}