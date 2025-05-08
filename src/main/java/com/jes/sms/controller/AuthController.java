package com.jes.sms.controller;

import com.jes.sms.dto.LoginResponseDTO;
import com.jes.sms.dto.UserDTO;
import com.jes.sms.entity.User;
import com.jes.sms.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid UserDTO registerUserDto) {
        User user = authService.signup(registerUserDto);
        LoginResponseDTO loginResponse = authService.getLoginResponseDTO(user);
        return ResponseEntity.ok(loginResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody UserDTO loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);
        LoginResponseDTO loginResponse = authService.getLoginResponseDTO(authenticatedUser);
        return ResponseEntity.ok(loginResponse);
    }
}
