package ua.project.dropmarket.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.entity.Product;
import ua.project.dropmarket.entity.Users;
import ua.project.dropmarket.repository.ProductRepository;
import ua.project.dropmarket.repository.UserRepository;
import ua.project.dropmarket.service.CustomerService;
import ua.project.dropmarket.service.ProductService;
import ua.project.dropmarket.service.UserService;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Controller
public class Management {

    private final CustomerService customerService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public Management(CustomerService customerService, UserRepository userRepository, UserService userService, ProductRepository productRepository, ProductService productService) {
        this.customerService = customerService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, Principal principal) {
        List<Product> currentUserProducts = new ArrayList<>();
        List<Product> otherUserProducts = new ArrayList<>();
        if (principal != null) {
            String username = principal.getName();
            Users currentUser = userRepository.findByUsername(username);
            currentUserProducts = productService.findByCreatedBy(currentUser);
            model.addAttribute("username", username);
        }

        List<Product> allProducts = productService.findAll();
        for (Product product : allProducts) {
            if (!currentUserProducts.contains(product)) {
                otherUserProducts.add(product);
            }
        }

        currentUserProducts.addAll(otherUserProducts);
        model.addAttribute("products", currentUserProducts);
        return "main";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/logout")
    public String logoutUser() {
        return "redirect:/";
    }

    @GetMapping("/regis")
    @PreAuthorize("isAnonymous()")
    public String getRegistrationPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        model.addAttribute("users", new Users());
        model.addAttribute("customer", new Customer());
        return "regis";
    }

    @PostMapping("/regis")
    public String addCustomer(@Valid Users users, BindingResult bindingResult, @Valid Customer customer, BindingResult bindingResult1, Model model) {
        if (bindingResult.hasErrors() || bindingResult1.hasErrors()) {
            return "regis";
        }

        if (userService.getLogicByUser(users.getUsername())) {
            model.addAttribute("message", "Користувач з таким іменем вже існує!");
            return "regis";
        }

        Users user = userService.saveNewUserToDB(users);
        customer.setUser(user);
        customerService.saveCustomerToDB(customer);
        return "redirect:/";
    }

    @GetMapping("/info")
    public String getInfoPage(Model model, Principal principal) {
        List<Product> currentUserProducts = new ArrayList<>();
        List<Product> otherUserProducts = new ArrayList<>();
        if (principal != null) {
            String username = principal.getName();
            Users currentUser = userRepository.findByUsername(username);
            currentUserProducts = productService.findByCreatedBy(currentUser);
            model.addAttribute("username", username);
        }

        List<Product> allProducts = productService.findAll();
        for (Product product : allProducts) {
            if (!currentUserProducts.contains(product)) {
                otherUserProducts.add(product);
            }
        }

        currentUserProducts.addAll(otherUserProducts);
        model.addAttribute("products", currentUserProducts);
        return "info";
    }

    @GetMapping("/products")
    public String getProductPage(Principal principal, Model model) {
        List<Product> currentUserProducts = new ArrayList<>();
        if (principal != null) {
            String username = principal.getName();
            Users currentUser = userRepository.findByUsername(username);
            currentUserProducts = productService.findByCreatedBy(currentUser);
            model.addAttribute("username", username);
        }

        model.addAttribute("products", currentUserProducts);
        return "products";
    }

    @PostMapping("/products")
    public String addProduct(@Valid Product product, @RequestParam("photoUrl") String photoUrl, Principal principal) {
        product.setPhoto(photoUrl);
        String username = principal.getName();
        Users createdBy = userRepository.findByUsername(username);
        product.setCreatedBy(createdBy);
        productService.saveDate(product);
        return "redirect:/products";
    }

    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return "redirect:/profile";
    }

    @GetMapping("/details/{productId}")
    public String getDetailsPage(@PathVariable("productId") Long productId, Model model, Principal principal) {
        List<Product> currentUserProducts = new ArrayList<>();
        List<Product> otherUserProducts = new ArrayList<>();
        if (principal != null) {
            String username = principal.getName();
            Users currentUser = userRepository.findByUsername(username);
            currentUserProducts = productService.findByCreatedBy(currentUser);
            model.addAttribute("username", username);
        }

        List<Product> allProducts = productService.findAll();
        for (Product product : allProducts) {
            if (!currentUserProducts.contains(product)) {
                otherUserProducts.add(product);
            }
        }

        currentUserProducts.addAll(otherUserProducts);
        model.addAttribute("products", currentUserProducts);

        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/";
        }

        model.addAttribute("product", product);
        return "details";
    }

    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("producer") String producer,
                                @RequestParam("price") BigDecimal price,
                                @RequestParam(value = "available", required = false) boolean available,
                                @RequestParam("photo") String photoUrl) {
        productService.updateProduct(productId, name, description, producer, price, available, photoUrl);
        return "redirect:/profile";
    }

    @PostMapping("/updateCustomer/{customerId}")
    public String updateCustomer(@PathVariable("customerId") Long customerId,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("email") String email,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("age") String age,
                                 @RequestParam("address") String address) {
        customerService.updateCustomer(customerId, firstName, lastName, email, phone, age, address);
        return "redirect:/profile";
    }

    @GetMapping("/cooperation")
    public String getCooperation(Model model, Principal principal) {
        List<Product> currentUserProducts = new ArrayList<>();
        List<Product> otherUserProducts = new ArrayList<>();
        if (principal != null) {
            String username = principal.getName();
            Users currentUser = userRepository.findByUsername(username);
            currentUserProducts = productService.findByCreatedBy(currentUser);
            model.addAttribute("username", username);
        }

        List<Product> allProducts = productService.findAll();
        for (Product product : allProducts) {
            if (!currentUserProducts.contains(product)) {
                otherUserProducts.add(product);
            }
        }

        currentUserProducts.addAll(otherUserProducts);
        model.addAttribute("products", currentUserProducts);
        return "cooperation";
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(Principal principal) {
        String username = principal.getName();
        userService.deleteUserByUsername(username);
        return "redirect:/logout";
    }

    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, Principal principal) {
        String username = principal.getName();
        customerService.saveAvatar(username, file);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model, Principal principal) {
        String username = principal.getName();
        Customer customer = customerService.getCustomerByUsername(username);
        model.addAttribute("customer", customer);

        List<Product> products = productService.findByCreatedBy(customer.getUser());
        model.addAttribute("products", products);
        if (customer.getAvatar() != null) {
            String base64Avatar = customerService.bytesToBase64String(customer.getAvatar());
            model.addAttribute("avatar", base64Avatar);
        }
        return "profile";
    }

    @PostMapping("/deleteAvatar")
    public String deleteAvatar(Principal principal) {
        String username = principal.getName();
        customerService.deleteAvatar(username);
        return "redirect:/profile";
    }
}