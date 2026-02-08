package com.basic.adminowner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basic.adminowner.entity.Property;
import com.basic.adminowner.repository.PropertyRepository;
import com.basic.adminowner.entity.User;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/owner")


public class PropertyController {
    private final PropertyRepository repo;
    public PropertyController(PropertyRepository repo) {
        this.repo = repo;
    }
        @GetMapping("/dashboard")
        public String dashboard(Model model, HttpSession session) {
            User owner = (User) session.getAttribute("loggedInUser");
            model.addAttribute("properties", repo.findByOwnerId(owner.getId()));
            return "owner-dashboard";

        }
        @PostMapping("/add")
        public String add(@RequestParam String name,
                @RequestParam String location,
                @RequestParam double price,
                HttpSession session) {
            User owner = (User) session.getAttribute("loggedInUser");
            Property p = new Property();
            p.setName(name);
            p.setLocation(location);
            p.setPrice(price);
            p.setOwner(owner);

            repo.save(p);
            return "redirect:/owner/dashboard";



    }
        @PostMapping("/delete/{id}")
        public String deleteProperty(@PathVariable Long id, HttpSession session) {
            User owner = (User) session.getAttribute("loggedInUser");
            Property property = repo.findById(id).orElseThrow();

            if (property.getOwner().getId().equals(owner.getId())) {
                repo.deleteById(id);
            }

            return "redirect:/owner/dashboard";
        }
        @GetMapping("/edit/{id}")
        public String editProperty(@PathVariable Long id,
                                   Model model,
                                   HttpSession session) {

            User owner = (User) session.getAttribute("loggedInUser");

            Property property = repo.findById(id).orElseThrow();


            if (!property.getOwner().getId().equals(owner.getId())) {
                return "redirect:/owner/dashboard";
            }

            model.addAttribute("property", property);
            return "edit-property";
        }
        @PostMapping("/update")
        public String updateProperty(@RequestParam Long id,
                                     @RequestParam String name,
                                     @RequestParam String location,
                                     @RequestParam double price,
                                     HttpSession session) {

            User owner = (User) session.getAttribute("loggedInUser");

            if (owner == null) {
                return "redirect:/login";
            }

            Property property = repo.findById(id).orElse(null);

            if (property == null ||
                !property.getOwner().getId().equals(owner.getId())) {
                return "redirect:/owner/dashboard";
            }

            property.setName(name);
            property.setLocation(location);
            property.setPrice(price);

            repo.save(property);
            return "redirect:/owner/dashboard";
        }



}
