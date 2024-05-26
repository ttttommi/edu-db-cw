package com.dblab.lab6.api.controller;

import com.dblab.lab6.api.dto.JwtResponse;
import com.dblab.lab6.api.dto.SignInDto;
import com.dblab.lab6.api.dto.SignUpDto;
import com.dblab.lab6.api.dto.UserDto;
import com.dblab.lab6.api.entity.User;
import com.dblab.lab6.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
        String jwt = authService.signIn(signInDto);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> getRegisterPage(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUpDto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.update(authService.getCurrentUser(), userDto));
    }
}
