package br.com.atlas.atlas_logistics.infrastructure.web.dtos;

import br.com.atlas.atlas_logistics.domain.model.relationalModels.users.UserRole;

import java.util.Set;

public record CreateAccountDTO(String name, String password, String email, Set<UserRole> roles) {
}
