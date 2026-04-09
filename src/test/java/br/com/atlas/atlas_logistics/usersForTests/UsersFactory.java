package br.com.atlas.atlas_logistics.usersForTests;

import br.com.atlas.atlas_logistics.domain.model.Role;
import br.com.atlas.atlas_logistics.domain.model.User;
import br.com.atlas.atlas_logistics.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class UsersFactory {

    @Autowired
     private PasswordEncoder encoder;




    public User createAdmin(){

        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@atlas.com");
        user.setPassword(encoder.encode("1234"));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserRole adminRole = new UserRole(user,new Role("ROLE_ADMIN"),true,LocalDateTime.now(),null,"test");
        UserRole operatorRole = new UserRole(user,new Role("ROLE_OPERATOR"),true,LocalDateTime.now(),null,"test");

        user.setRoles(Set.of(adminRole,operatorRole));

        return  user;

    }

    public User createOperator(){

        User user = new User();
        user.setUsername("operator");
        user.setEmail("operator@atlas.com");
        user.setPassword(encoder.encode("1234"));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserRole operatorRole = new UserRole(user,new Role("ROLE_OPERATOR"),true,LocalDateTime.now(),null,"test");

        user.setRoles(Set.of(operatorRole));


        return user;

    }


    public User createInventory(){
        User user = new User();
        user.setUsername("inventory");
        user.setEmail("inventory@atlas.com");
        user.setPassword(encoder.encode("1234"));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserRole inventoryRole = new UserRole(user,new Role("ROLE_INVENTORY"),true,LocalDateTime.now(),null,"test");


        user.setRoles(Set.of(inventoryRole));

        return user;

    }

    public User createAuditor(){
        User user = new User();
        user.setUsername("auditor");
        user.setEmail("auditor@atlas.com");
        user.setPassword(encoder.encode("1234"));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserRole auditorRole = new UserRole(user,new Role("ROLE_AUDITOR"),true,LocalDateTime.now(),null,"test");


        user.setRoles(Set.of(auditorRole));

        return user;
    }
}
