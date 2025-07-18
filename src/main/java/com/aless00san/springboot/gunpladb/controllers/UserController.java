package com.aless00san.springboot.gunpladb.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aless00san.springboot.gunpladb.entities.system.Role;
import com.aless00san.springboot.gunpladb.entities.system.User;
import com.aless00san.springboot.gunpladb.services.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, BindingResult result) {
        user.setSuperuser(false);
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.ok(userService.save(user));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Immediately expires the cookie
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/auth")
    public ResponseEntity<?> auth(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() == null || request.getCookies().length == 0) {
            return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(null);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            String[] roles = authentication.getAuthorities().stream()
                    .map(role -> role.getAuthority())
                    .toArray(String[]::new);

            if (username != "anonymousUser") {

                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("username", username);
                responseBody.put("roles", roles);

                return ResponseEntity.ok(responseBody);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User  is not authenticated");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User  is not authenticated");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/roles/{username}")
    public ResponseEntity<?> roles(@PathVariable String username) {
        if (username != "anonymousUser") {
            List<Role> roles = userService.findRolesByUsername(username);
            return ResponseEntity.ok().body(roles);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User  is not authenticated");

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new java.util.HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(
                error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
