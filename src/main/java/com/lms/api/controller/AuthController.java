package com.lms.api.controller;

import com.lms.api.model.User;
import com.lms.api.security.JwtUtil;
import com.lms.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

//    @PostMapping("/register")
//    public User register(@RequestBody User user) {
//        return userService.createUser(user);
//    }
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Set default role
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("STUDENT"); // default
        }
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        User user = userService.loginUser(email, password);

        // ✅ Generate JWT Token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // ✅ Build response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("user", user);
        return response;
    }
}
