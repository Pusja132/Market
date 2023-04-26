package ru.bandurin.marketplace.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.bandurin.marketplace.domain.entities.security.Permission;
import ru.bandurin.marketplace.domain.entities.security.Role;
import ru.bandurin.marketplace.domain.entities.marketplace.lot.Lot;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isVerificated;

    @Column(nullable = false)
    private Boolean isVerificatedByAdmins;

    @OneToOne(fetch = FetchType.EAGER)
    private Role role;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "author")
    private List<Lot> lots;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissions();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasAuthority(Permission.PermissionName permissionName) {
        for (GrantedAuthority authority : getAuthorities()) {
            if (authority.getAuthority().equalsIgnoreCase(permissionName.name()))  return true;
        }

        return false;
    }
}
