package com.proWheyBrasil.controller;

import com.proWheyBrasil.domain.user.User;
import com.proWheyBrasil.dto.LoginRequestDto;
import com.proWheyBrasil.dto.RegisterRequestDto;
import com.proWheyBrasil.dto.ResponseDto;
import com.proWheyBrasil.infra.security.TokenService;
import com.proWheyBrasil.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto body){
        User user = this.userRepository.findByLogin(body.login()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
                    return ResponseEntity.ok(new ResponseDto(user.getIdUser(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto body){
        Optional<User> user = this.userRepository.findByLogin(body.email());

        if (user.isEmpty()){
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setLogin(body.email());
        newUser.setIdUser(body.idUser());
        this.userRepository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDto(newUser.getIdUser(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
