package ru.bandurin.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bandurin.marketplace.payload.user.UserDto;
import ru.bandurin.marketplace.service.auth.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody UserDto dto) {
        return new ResponseEntity<>(service.registration(dto), HttpStatus.OK);
    }
}
