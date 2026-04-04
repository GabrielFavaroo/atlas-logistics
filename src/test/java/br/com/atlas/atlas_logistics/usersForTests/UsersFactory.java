package br.com.atlas.atlas_logistics.usersForTests;

import br.com.atlas.atlas_logistics.domain.model.User;

import java.time.LocalDateTime;
import java.util.Set;

public class UsersFactory {

    public User createAdmin(){
        return new User("admin",
                "admin@atlas.com",
                "1234",
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Set.of("ADMIN", "OPERATOR"));

    }

    public User createOperator(){
        return new User(
                "operator",
                "operator@atlas.com",
                "1234",
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Set.of("OPERATOR")
        );

    }

    public User createInventory(){
        return new User(
                "inventory",
                "inventory@atlas.com",
                "1234",
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Set.of("INVENTORY")
        );

    }

    public User createAuditor(){
        return new User(
                "auditor",
                "auditor@atlas.com",
                "1234",
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Set.of("AUDITOR")
        );
    }
}
