package com.frontend.financetracker.Controllers;

import com.frontend.financetracker.Services.TransactionService;
import com.frontend.financetracker.Models.User;
import com.frontend.financetracker.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    private final UserService userService;
    private final TransactionService transactionService;

    public UserController(UserService userService, TransactionService transactionService) {this.userService=userService;
        this.transactionService = transactionService;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, Model model) {
        boolean isAuthenticated = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            return "redirect:/dashboard"; // Redirect to the dashboard on successful login
        } else {
            model.addAttribute("loginError", "Invalid username or password. Please try again.");
            return "login"; // Return to the login page on failure
        }
    }





    @RequestMapping("/registerUser")
    public String showRegisterPage(Model model) {
        User user=new User();
        model.addAttribute("user",user);
        return "register";
    }
    @PostMapping("/registerUser")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage,e.getMessage()");
            return "register";

        }else{
            System.out.println("User created : ID :" + userService.registerUser(user));
            return"redirect:/login";



        }
    }



}
