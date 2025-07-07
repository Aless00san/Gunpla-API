package com.aless00san.springboot.gunpladb.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aless00san.springboot.gunpladb.entities.system.User;
import com.aless00san.springboot.gunpladb.services.IUserService;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public List<User> findAll() {
        return userService.findAll();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, BindingResult result) {
        user.setSuperuser(false);
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.ok(userService.save(user));
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new java.util.HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(
                error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
