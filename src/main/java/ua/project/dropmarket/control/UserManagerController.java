package ua.project.dropmarket.control;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.project.dropmarket.entity.Customer;
import ua.project.dropmarket.entity.Users;
import ua.project.dropmarket.service.CustomerManagerService;
import ua.project.dropmarket.service.UserManagerService;

@Controller
public class UserManagerController {
    private final CustomerManagerService customerService;
    private final UserManagerService userService;

    @Autowired
    public UserManagerController(CustomerManagerService customerService, UserManagerService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("/regis")
    @PreAuthorize("isAnonymous()")
    public String getRegistrationPage(Model model) {
        model.addAttribute("users", new Users());
        model.addAttribute("customer", new Customer());
        return "regis";
    }

    @PostMapping("/regis")
    public String saveNewCustomer(@Valid Users users, BindingResult bindingResult,
                                  @Valid Customer customer, BindingResult bindingResult1, Model model) {

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
    @PreAuthorize("permitAll()")
    public String getInfoPage() {
        return "info";
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile";
    }
}