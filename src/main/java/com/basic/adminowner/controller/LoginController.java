package com.basic.adminowner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basic.adminowner.exception.InvalidLoginException;
import com.basic.adminowner.service.UserService;
import com.basic.adminowner.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller

public class LoginController {
    private final UserService service;
    
    public LoginController(UserService service) {
        this.service = service;
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        User user = service.findByEmail(email);
        if (!user.getPassword().equals(password)) {
            throw new InvalidLoginException("Invalid login credentials");
        }
        if (!user.isEnabled()) {
            model.addAttribute("error", "Account Disabled by Admin");
            return "login";
        }
        session.setAttribute("loggedInUser", user);
        if (user.isFirstLogin()) {
            return "redirect:/change-password";
        }
        return user.getRole().name().equals("ADMIN") ?
                "redirect:/admin/dashboard" :
                    "redirect:/owner/dashboard";
    }
    @GetMapping("/change-password")
    public String changePasswordPage() {
        return "change-password";
    }
    @PostMapping("/change-password")
    public String updatePassword(@RequestParam String newPassword,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        service.updatePassword(user, newPassword);
        return "redirect:/owner/dashboard";
    } 

}
