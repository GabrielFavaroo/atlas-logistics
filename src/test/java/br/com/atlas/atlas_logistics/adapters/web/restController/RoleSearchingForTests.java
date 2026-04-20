package br.com.atlas.atlas_logistics.adapters.web.restController;

import br.com.atlas.atlas_logistics.adapters.persistence.RoleRepository;
import br.com.atlas.atlas_logistics.domain.model.Role;
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
