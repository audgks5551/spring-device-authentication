package io.mhan.springsecuritydevice.security.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class SecurityUser implements SecurityUserAdapter {
    private final Long id;
    private final String username;
    private final String password;
    private final List<String> deviceIds;
    private boolean isDeviceAuthenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public void deviceAuthenticationComplete() {
        this.isDeviceAuthenticated = true;
    }
}
