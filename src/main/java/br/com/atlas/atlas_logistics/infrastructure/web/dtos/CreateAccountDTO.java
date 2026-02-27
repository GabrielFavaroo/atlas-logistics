package br.com.atlas.atlas_logistics.infrastructure.web.dtos;

import br.com.atlas.atlas_logistics.domain.model.UserRole;

import java.util.Set;

public record CreateAccountDTO(String name, String password, String email, Set<String> roles) {
}
