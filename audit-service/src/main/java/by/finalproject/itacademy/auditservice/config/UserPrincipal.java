package by.finalproject.itacademy.auditservice.config;

import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserPrincipal implements UserDetails {
    private UUID uuid;
    private String mail;
    private String fio;
    private String role;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UUID uuid, String mail, String fio, String role,
                         Collection<? extends GrantedAuthority> authorities) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserDTO user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
        );

        return new UserPrincipal(
                user.getUuidUser(),
                user.getMail(),
                user.getFio(),
                user.getRole(),
                authorities
        );
    }

    public UUID getUuid() { return uuid; }
    public String getRole() { return role; }

    @Override public String getUsername() { return mail; }
    @Override public String getPassword() { return fio; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
