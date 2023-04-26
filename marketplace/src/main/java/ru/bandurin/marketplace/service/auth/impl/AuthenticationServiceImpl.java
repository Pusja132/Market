package ru.bandurin.marketplace.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.User_;
import ru.bandurin.marketplace.domain.entities.security.Role_;
import ru.bandurin.marketplace.domain.mapper.UserMapper;
import ru.bandurin.marketplace.domain.repositories.RoleRepository;
import ru.bandurin.marketplace.domain.repositories.UserRepository;
import ru.bandurin.marketplace.payload.user.*;
import ru.bandurin.marketplace.service.auth.AuthenticationService;

import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationDto authorization(AuthorizationRequest request) {
        return null;
    }

    @Override
    public UserDto registration(UserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail()) || userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("ACCOUNT ALREADY CREATED");
        }

        User user = User.builder()
                .id(null)
                .email(dto.getEmail())
                .username(dto.getUsername())
                .isVerificated(true) // temp solution
                .role(roleRepository.findByName("USER")
                        .orElseThrow(() -> new NotFoundException(
                                User_.class.getName(),
                                Map.of(Role_.ID, "USER"))
                        )
                )
                .lots(new ArrayList<>())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isVerificatedByAdmins(false)
                .build();

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {

    }

    @Override
    public void changeEmail(ChangeEmailRequest request) {

    }
}
