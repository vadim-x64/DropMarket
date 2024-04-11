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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.project.dropmarket.entity.Category;
import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.entity.Product;
import ua.project.dropmarket.entity.Users;
import ua.project.dropmarket.repos.UserRepository;
import ua.project.dropmarket.service.CategoryService;
import ua.project.dropmarket.service.CustomerManagerService;
import ua.project.dropmarket.service.ProductService;
import ua.project.dropmarket.service.UserManagerService;
import java.security.Principal;
import java.util.List;

@Getter
@Controller
public class UserManagerController {

    private final CustomerManagerService customerService;
    private final UserRepository userRepository;
    private final UserManagerService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public UserManagerController(CustomerManagerService customerService, UserManagerService userService, UserRepository userRepository, ProductService productService, CategoryService categoryService) {
        this.customerService = customerService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, Principal principal) {


        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        List<Product> products = productService.getAllProducts();
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
        return "profile";
    }

    @GetMapping("/details/{id}")
    public String getProductDetails(@PathVariable ("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "details";
    }

    @GetMapping("/categories")
    public String getCategoryPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/categories/{categoryId}/products")
    public String getProductPage(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        List<Product> products = category.getProducts();
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "products";
    }
}