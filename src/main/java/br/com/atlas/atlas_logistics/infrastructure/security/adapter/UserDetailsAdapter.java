package br.com.atlas.atlas_logistics.infrastructure.security.adapter;

import br.com.atlas.atlas_logistics.domain.model.relationalModels.users.User;
import br.com.atlas.atlas_logistics.domain.model.relationalModels.users.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsAdapter implements UserDetails {

    private final User user;

    public UserDetailsAdapter(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRoles() == null){
            return Collections.emptyList();
        }

        return user.getRoles().stream()
                .filter(ur -> Boolean.TRUE.equals(ur.getActive()))
                .map(UserRole :: getRole)
                .map(role -> new SimpleGrantedAuthority(role.getAuthority())).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return user.isEnabled();
    }
}
