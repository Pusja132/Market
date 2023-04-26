package ru.bandurin.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bandurin.marketplace.payload.user.UserDto;
import ru.bandurin.marketplace.service.user.UserService;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    
    @GetMapping("/{id}") 
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> get(@PathVariable String email) {
        return new ResponseEntity<>(service.get(email), HttpStatus.OK);
    }

    @GetMapping("/self")
    public ResponseEntity<UserDto> get() {
        return new ResponseEntity<>(service.getSelfDto(), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws AuthenticationException {
        service.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
