package ru.bandurin.marketplace.payload.user;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private Boolean isVerificated;
    private Boolean getIsVerificatedByAdmins;
    private Long roleId;
    private List<Long> lotIds;
}
