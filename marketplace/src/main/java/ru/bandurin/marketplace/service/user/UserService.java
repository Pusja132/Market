package ru.bandurin.marketplace.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.payload.user.UserDto;

import javax.security.sasl.AuthenticationException;

public interface UserService extends UserDetailsService {
    User getEntity(Long id);
    User getEntity(String email);
    User getSelf();

    UserDto get(Long id);
    UserDto get(String email);
    UserDto getSelfDto();
    UserDto update(Long id, UserDto dto);

    void delete(Long id) throws AuthenticationException;

}
