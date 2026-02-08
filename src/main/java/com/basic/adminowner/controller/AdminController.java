package com.basic.adminowner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basic.adminowner.service.UserService;

@Controller
@RequestMapping("/admin")

public class AdminController {
    private final UserService service;
    public AdminController(UserService service) {
        this.service = service;
    }
            @GetMapping("/dashboard")
        public String dashboard(Model model) {
            model.addAttribute("owners", service.getAllOwners());  
            return "admin-dashboard";
        }

        @GetMapping("/create-owner")
        public String createOwnerPage() {
            return "admin-dashboard";
        }

        @PostMapping("/create-owner")
        public String createOwner(@RequestParam String name,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  Model model) {
            try {
                service.createOwner(name, email, password);
                model.addAttribute("success", "Owner created successfully!");
            } catch (RuntimeException e) {
                model.addAttribute("error", e.getMessage());
            }
            return "admin-dashboard";
        }

        @GetMapping("/disable/{id}")
        public String disable(@PathVariable Long id) {
            service.enableDisable(id, false);
            return "redirect:/admin/dashboard";
        }
        @GetMapping("/enable/{id}")
        public String enable(@PathVariable Long id) {
            service.enableDisable(id, true);
            return "redirect:/admin/dashboard";
        }

}
