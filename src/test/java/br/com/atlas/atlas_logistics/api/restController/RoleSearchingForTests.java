package br.com.atlas.atlas_logistics.api.restController;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.repositories.RoleRepository;
import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.users.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleSearchingForTests {
    @Autowired
    RoleRepository roleRepository;

    public Role findRole(String roleName){

        return roleRepository.findRoleByName(roleName).orElseThrow(()->new RuntimeException("Role não encontrado nos registros da base de dados"));

    }
}
