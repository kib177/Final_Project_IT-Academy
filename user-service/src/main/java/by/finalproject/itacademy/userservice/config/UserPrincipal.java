package by.finalproject.itacademy.userservice.config;

import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import by.finalproject.itacademy.userservice.model.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

// UserPrincipal.java
public class UserPrincipal implements UserDetails {
    private UUID uuid;
    private String mail;
    private String password;
    private UserRole role;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UUID uuid, String mail, String password, UserRole role,
                         Collection<? extends GrantedAuthority> authorities) {
        this.uuid = uuid;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserEntity user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        return new UserPrincipal(
                user.getUuid(),
                user.getMail(),
                user.getPassword(),
                user.getRole(),
                authorities
        );
    }

    public UUID getUuid() { return uuid; }
    public UserRole getRole() { return role; }

    @Override public String getUsername() { return mail; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
