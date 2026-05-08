package br.com.atlas.atlas_logistics.infrastructure.security.service;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.users.User;
import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.repositories.UserRepository;
import br.com.atlas.atlas_logistics.infrastructure.security.adapter.UserDetailsAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado na base de dados"));

        return new UserDetailsAdapter(user);
    }
}
