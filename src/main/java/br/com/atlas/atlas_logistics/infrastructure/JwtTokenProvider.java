package br.com.atlas.atlas_logistics.infrastructure;

import br.com.atlas.atlas_logistics.domain.model.Role;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Autowired
    private UserDetailsService userDetailsService;


    @Value("${my.app.secret:fallback_default}")
    private String secretKey = "segredo";

    @Value("${my.app.secret:fallback_default}")
    private long validityInMinutes;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());

    }

    public TokenDTO createAccessToken(String username, List<Role> roles){
        Date now = new Date();
        Date validity = new Date(now.getTime()+validityInMinutes);
        String accessToken = getAccessToken(username, roles,now, validity);
        String refreshToken = getRefreshToken(username, roles, now);
        return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
    }

    private String getRefreshToken(String username, List<Role> roles, Date now) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return "";
    }

    private String getAccessToken(String username, List<Role> roles, Date validity, Date date) {
        return "";
    }


}
