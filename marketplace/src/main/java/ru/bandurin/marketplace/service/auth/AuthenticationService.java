package ru.bandurin.marketplace.service.auth;

import ru.bandurin.marketplace.payload.user.*;

public interface AuthenticationService {
    AuthenticationDto authorization(AuthorizationRequest request);

    UserDto registration(UserDto dto);

    void changePassword(ChangePasswordRequest request);

    void changeEmail(ChangeEmailRequest request);
}
