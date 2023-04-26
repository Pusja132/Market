package ru.bandurin.marketplace.domain.mapper;

import org.springframework.stereotype.Component;
import ru.bandurin.marketplace.domain.entities.User;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;
import ru.bandurin.marketplace.payload.user.UserDto;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .password(null)
                .getIsVerificatedByAdmins(entity.getIsVerificatedByAdmins())
                .isVerificated(entity.getIsVerificated())
                .roleId(entity.getRole().getId())
                .lotIds(entity.getLots()
                        .stream()
                        .map(Lot::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public void update(User entity, UserDto dto) {
        if (dto.getEmail() != null
                && !dto.getEmail().isBlank()
                && !dto.getEmail().equals(entity.getEmail())
        ) {
            entity.setEmail(dto.getEmail());
        }

        if (dto.getUsername() != null
                && !dto.getUsername().isBlank()
                && !dto.getUsername().equals(entity.getUsername())
        ) {
            entity.setUsername(dto.getUsername());
        }

        if (dto.getIsVerificated() != null && dto.getIsVerificated() != entity.getIsVerificated()) {
            entity.setIsVerificated(!entity.getIsVerificated());
        }

        if (dto.getGetIsVerificatedByAdmins() != null && dto.getGetIsVerificatedByAdmins() != entity.getIsVerificatedByAdmins()) {
            entity.setIsVerificatedByAdmins(!entity.getIsVerificatedByAdmins());
        }
    }
}
