package br.com.atlas.atlas_logistics.infrastructure.web.controller;



import br.com.atlas.atlas_logistics.infrastructure.web.dtos.AccountCredentialsDTO;
import br.com.atlas.atlas_logistics.infrastructure.security.service.AuthServices;
import br.com.atlas.atlas_logistics.infrastructure.web.dtos.CreateAccountDTO;
import br.com.atlas.atlas_logistics.infrastructure.web.dtos.TokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthServices authServices;

    public AuthenticationController(AuthServices authServices) {
        this.authServices = authServices;
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> login (@RequestBody AccountCredentialsDTO requestDto){


        var token = authServices.signIn(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);

    }

    @PostMapping("/signup")
    public ResponseEntity<Void> createUser (@RequestBody CreateAccountDTO createAccountDTO){
        authServices.createUser(createAccountDTO);
        return ResponseEntity.ok().build();
    }




    @PutMapping("/refresh/{username}")
    public ResponseEntity<TokenDTO> refresh (@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken){
        if(!authServices.areParametersValid(username,refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        var token = authServices.signWithRefreshtoken(username,refreshToken);
        return  ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
