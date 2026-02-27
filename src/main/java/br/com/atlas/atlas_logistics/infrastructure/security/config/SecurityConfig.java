package br.com.atlas.atlas_logistics.infrastructure.security.config;

import br.com.atlas.atlas_logistics.infrastructure.security.jwt.JwtTokenFilter;
import br.com.atlas.atlas_logistics.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }



    @Bean
    PasswordEncoder passwordEncoder(){



        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("",
                8,
                185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        HashMap<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2" , pbkdf2Encoder);

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

        return passwordEncoder;
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);

        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers("/auth/signin",
                                        "/auth/refresh/**",
                                        "/swagger-ui/***",
                                        "/v3/api-docs").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/products/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/products").hasAuthority("OPERATOR")
                                .requestMatchers(HttpMethod.PUT,"/products/**").hasAuthority("OPERATOR")
                                .requestMatchers(HttpMethod.PATCH,"/products").hasAuthority("INVENTORY")
                                .requestMatchers(HttpMethod.GET,"/products/**").hasAuthority("AUDITOR")
                                .requestMatchers(HttpMethod.GET,"/products").hasAuthority("AUDITOR")

                                .requestMatchers("/products/**").authenticated()


                                .requestMatchers("/users").denyAll()
                                .requestMatchers("/auth/signup").authenticated()

                )

                .cors(cors->{})
                .build();
    }

    @Bean
    public RoleHierarchy roleHierarchy (){

        return RoleHierarchyImpl.fromHierarchy("ADMIN > OPERATOR > INVENTORY > AUDITOR");

    }


}
