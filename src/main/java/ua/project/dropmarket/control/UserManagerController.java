package ua.project.dropmarket.control;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.entity.Product;
import ua.project.dropmarket.entity.Users;
import ua.project.dropmarket.repos.ProductRepository;
import ua.project.dropmarket.repos.UserRepository;
import ua.project.dropmarket.service.CustomerManagerService;
import ua.project.dropmarket.service.ProductService;
import ua.project.dropmarket.service.UserManagerService;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Controller
public class UserManagerController {

    private final CustomerManagerService customerService;
    private final UserRepository userRepository;
    private final UserManagerService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public UserManagerController(ProductRepository productRepository, CustomerManagerService customerService, UserManagerService userService, UserRepository userRepository, ProductService productService) {
        this.customerService = customerService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String getHomePage(Model model, Principal principal) {

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }


        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "main";
    }

    @GetMapping("/login")
    public String getLoginPage() {
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
    public String saveNewCustomer(@Valid Users users, BindingResult bindingResult, @Valid Customer customer, BindingResult bindingResult1, Model model) {

        if (bindingResult.hasErrors() || bindingResult1.hasErrors()) {
            return "/regis";
        }

        if (userService.getLogicByUser(users.getUsername())) {
            model.addAttribute("message", "Користувач з таким іменем вже існує!");
            return "/regis";
        }

        Users user1 = userService.saveNewUserToDB(users);
        customer.setUser(user1);
        customerService.saveCustomerToDB(customer);
        return "redirect:/";
    }

    @GetMapping("/info")
    public String getInfoPage() {
        return "info";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model, Principal principal) {
        String username = principal.getName();
        Customer customer = customerService.getCustomerByUsername(username);
        model.addAttribute("customer", customer);

        List<Product> products = productService.findByCreatedBy(customer.getUser());
        model.addAttribute("products", products);

        return "profile";
    }

    @GetMapping("/products")
    public String showAddProductForm(Product product) {
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
    public String getProductDetails(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/";
        }
        model.addAttribute("product", product);
        return "details";
    }
}