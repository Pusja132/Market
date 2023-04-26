package ru.bandurin.marketplace.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.User_;
import ru.bandurin.marketplace.domain.entities.security.Permission;
import ru.bandurin.marketplace.domain.repositories.UserRepository;
import ru.bandurin.marketplace.core.exception.NotFoundException;
import ru.bandurin.marketplace.domain.mapper.UserMapper;
import ru.bandurin.marketplace.payload.user.UserDto;
import ru.bandurin.marketplace.service.user.UserService;

import javax.security.sasl.AuthenticationException;
import java.util.Map;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public User getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        User.class.toString(),
                        Map.of(User_.ID, id)
        ));
    }

    @Override
    public User getEntity(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        User.class.toString(),
                        Map.of(User_.EMAIL, email)
        ));
    }

    private User getEntityByUserName(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        User.class.toString(),
                        Map.of(User_.USERNAME, username)
                ));
    }

    @Override
    public User getSelf() {
        System.out.println(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());

        return getEntityByUserName(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }

    @Override
    public UserDto get(Long id) {
        return mapper.toDto(getEntity(id));
    }

    @Override
    public UserDto get(String email) {
        return mapper.toDto(getEntity(email));
    }

    @Override
    public UserDto getSelfDto() {
        return mapper.toDto(getSelf());
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User user = getEntity(id);
        mapper.update(user, dto);

        return mapper.toDto(repository.save(user));
    }

    @Override
    public void delete(Long id) throws AuthenticationException {
        User currentUser = getSelf();

        if (!currentUser.getId().equals(id) && !currentUser.hasAuthority(Permission.PermissionName.ADMIN)) {
            throw new AuthenticationException("FORBIDDEN");
        }

        repository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getEntityByUserName(username);
    }
}
