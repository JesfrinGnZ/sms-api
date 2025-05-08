package com.jes.sms.security.service;

import com.jes.sms.dto.LoginResponseDTO;
import com.jes.sms.dto.UserDTO;
import com.jes.sms.entity.User;
import com.jes.sms.exception.UserException;
import com.jes.sms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }



    /**
     * This method is used to register a new user.
     * @param input UserDTO
     * @return User
     */
    public User signup(UserDTO input) {

        Optional<User> userOptional =  userRepository.findByEmail(input.getEmail());
        if(userOptional.isPresent())
            throw new UserException("User is already registered", HttpStatus.BAD_REQUEST);


        User user = new User();
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        return userRepository.save(user);

    }

    /**
     * This method is used to authenticate a user.
     * @param input UserDTO
     * @return User
     */
    public User authenticate(UserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserException("Invalid credentials", HttpStatus.UNAUTHORIZED));
    }


    /**
     * This method is used to get the login response.
     * @param authenticatedUser User
     * @return LoginResponseDTO with the token and expiration time
     */
    public LoginResponseDTO getLoginResponseDTO(User authenticatedUser) {
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO();

        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return loginResponse;
    }

}
