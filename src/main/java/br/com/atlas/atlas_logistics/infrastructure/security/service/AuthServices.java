package br.com.atlas.atlas_logistics.infrastructure.security.service;

import br.com.atlas.atlas_logistics.domain.model.relationalModels.users.User;
import br.com.atlas.atlas_logistics.domain.model.relationalModels.users.UserRole;
import br.com.atlas.atlas_logistics.infrastructure.persistence.UserRepository;
import br.com.atlas.atlas_logistics.infrastructure.security.jwt.JwtTokenProvider;
import br.com.atlas.atlas_logistics.infrastructure.web.dtos.AccountCredentialsDTO;
import br.com.atlas.atlas_logistics.infrastructure.web.dtos.CreateAccountDTO;
import br.com.atlas.atlas_logistics.infrastructure.web.dtos.TokenDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class AuthServices {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Map<String,List<String>> userCreationCapabilitys = Map.of(

            "ADMIN", List.of("OPERATOR","INVENTORY","AUDITOR"),
            "OPERATOR", List.of("INVENTORY","AUDITOR"),
            "INVENTORY", List.of("AUDITOR"),
            "AUDITOR", List.of());

    public AuthServices(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(CreateAccountDTO createAccountDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> creatorRoles = authentication.getAuthorities();

        for(UserRole role : createAccountDTO.roles()){

            if(!userMayCreateUser(creatorRoles,role)){
                throw new AccessDeniedException("Você não possui permissão para criar este usuário na base de dados");
            }
        }

         LocalDateTime date = LocalDateTime.now();

        String encodedPassword = passwordEncoder.encode(createAccountDTO.password());
        User user = new User(createAccountDTO.name(), createAccountDTO.email(), encodedPassword, true, date,date,createAccountDTO.roles());

        userRepository.save(user);



    }



    public boolean userMayCreateUser(Collection<? extends GrantedAuthority> userAuthorities, UserRole requestedRole){

         return userAuthorities.stream()
                 .map(GrantedAuthority::getAuthority)
                 .anyMatch(role -> userCreationCapabilitys.getOrDefault(role, List.of())
                         .contains(requestedRole));
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
        return jwtTokenProvider.createAccessToken(credentials.username(), listRoles);
    }

    public TokenDTO signWithRefreshtoken (String username,String refreshToken){
        var user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Nome de usuario:" + username + "não encontrado"));
        TokenDTO tokenResponse;
        tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
        return tokenResponse;
    }


    public boolean areParametersValid(String username, String refreshToken) {
        return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(refreshToken);
    }
}
