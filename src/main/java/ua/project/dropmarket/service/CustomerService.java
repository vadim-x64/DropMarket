package ua.project.dropmarket.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.repository.CustomerRepository;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
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

    public void saveAvatar(String username, MultipartFile file) {
        Customer customer = customerRepository.findByUserUsername(username);
        try {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            List<String> allowedExtensions = Arrays.asList("ico", "bmp", "png", "jpg", "jpeg");
            if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
                return;
            }

            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            BufferedImage thumbnail = Thumbnails.of(originalImage).size(200, 200).asBufferedImage();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "jpg", out);
            byte[] bytes = out.toByteArray();
            customer.setAvatar(bytes);
            customerRepository.save(customer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String bytesToBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public void deleteAvatar(String username) {
        Customer customer = customerRepository.findByUserUsername(username);
        customer.setAvatar(null);
        customerRepository.save(customer);
    }
}