package br.com.atlas.atlas_logistics.infrastructure.web.dtos;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.users.UserRole;

import java.util.Set;

public record CreateAccountDTO(String name, String password, String email, Set<UserRole> roles) {
}
