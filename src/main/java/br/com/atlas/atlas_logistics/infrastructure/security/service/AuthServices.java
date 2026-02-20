package br.com.atlas.atlas_logistics.infrastructure.security.service;

import br.com.atlas.atlas_logistics.domain.model.UserRole;
import br.com.atlas.atlas_logistics.infrastructure.persistence.UserRepository;
import br.com.atlas.atlas_logistics.infrastructure.security.jwt.JwtTokenProvider;
import br.com.atlas.atlas_logistics.infrastructure.web.dtos.AccountCredentialsDTO;
import br.com.atlas.atlas_logistics.infrastructure.web.dtos.TokenDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServices {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;


    public AuthServices(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public TokenDTO signIn(AccountCredentialsDTO credentials){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.username(),
                        credentials.password()

                )

        );
        var user = userRepository.findByUsername(credentials.username()).orElseThrow(() -> new UsernameNotFoundException("Nome de usuario:" + credentials.username() + "não encontrado"));


        List<String> listRoles = new ArrayList<>();
        for(UserRole ur : user.getRoles()){
            if(ur.getActive()){
            listRoles.add(ur.getRole().getName());}


        }
        var tokenResponse = jwtTokenProvider.createAccessToken(credentials.username(), listRoles);
        return tokenResponse;
    }




}
