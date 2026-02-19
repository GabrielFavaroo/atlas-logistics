package br.com.atlas.atlas_logistics.infrastructure;

import br.com.atlas.atlas_logistics.domain.model.Role;
import br.com.atlas.atlas_logistics.domain.model.UserRole;
import br.com.atlas.atlas_logistics.infrastructure.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.username(),
                        credentials.password()

                )

        );
        var user = userRepository.findByUsername(credentials.username()).orElseThrow(() -> new UsernameNotFoundException("Nome de usuario:" + credentials.username() + "não encontrado"));


        List<Role> listRoles = new ArrayList<>();
        for(UserRole ur : user.getRoles()){
            if(ur.getActive()){
            listRoles.add(ur.getRole());}


        }
        var tokenResponse = jwtTokenProvider.createAccessToken(credentials.username(), listRoles);
        return ResponseEntity.ok(tokenResponse);
    }

}
