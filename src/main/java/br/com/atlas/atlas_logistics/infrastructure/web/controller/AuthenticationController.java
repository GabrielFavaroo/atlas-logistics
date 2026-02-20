package br.com.atlas.atlas_logistics.infrastructure.web.controller;



import br.com.atlas.atlas_logistics.infrastructure.web.dtos.AccountCredentialsDTO;
import br.com.atlas.atlas_logistics.infrastructure.security.service.AuthServices;
import br.com.atlas.atlas_logistics.infrastructure.web.dtos.TokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthServices authServices;

    public AuthenticationController(AuthServices authServices) {
        this.authServices = authServices;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login (@RequestBody AccountCredentialsDTO requestDto){

        var token = authServices.signIn(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);

    }

}
