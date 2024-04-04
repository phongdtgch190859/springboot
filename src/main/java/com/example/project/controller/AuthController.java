package com.example.project.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Request.LoginRequest;
import com.example.project.config.JwtProvider;
import com.example.project.entity.UserEntity;
import com.example.project.exception.UserException;
import com.example.project.repository.IUserRepository;
import com.example.project.response.AuthResponse;
import com.example.project.service.impl.CustomeUserService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private IUserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomeUserService customeUserService;

    public AuthController(IUserRepository userRepository,
            CustomeUserService customeUserService,
            PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.customeUserService = customeUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public String postMethodName(@RequestBody String entity) {
        // TODO: process POST request

        return entity;
    }

    @PostMapping("/sigup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserEntity user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String name = user.getName();
        UserEntity isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new UserException("email is already used with another account");
        }

        UserEntity createdUser = new UserEntity();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setName(name);

        UserEntity savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
                savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMesseage("Sigup success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/sigin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMesseage("Sigin success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customeUserService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("invalid username...");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password...");
        }
        // TODO Auto-generated method stub
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
    }
}
