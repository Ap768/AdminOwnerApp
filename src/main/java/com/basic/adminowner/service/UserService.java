package com.basic.adminowner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.basic.adminowner.exception.UserNotFoundException;
import com.basic.adminowner.repository.UserRepository;
import com.basic.adminowner.entity.Role;
import com.basic.adminowner.entity.User;

@Service
public class UserService {

    private final UserRepository repo;
    private final EmailService emailService;

    public UserService(UserRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    public void createOwner(String name, String email, String password) {

        if (repo.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.OWNER);
        user.setEnabled(true);
        user.setFirstLogin(true);

        repo.save(user);
        emailService.sendOwnerCredentials(email, email, password);
    }

    public List<User> getAllOwners() {
        return repo.findByRole(Role.OWNER);   // ✅ FIXED
    }

    public void enableDisable(Long id, boolean status) {
        User user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEnabled(status);
        repo.save(user);
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setFirstLogin(false);
        repo.save(user);
    }
}
