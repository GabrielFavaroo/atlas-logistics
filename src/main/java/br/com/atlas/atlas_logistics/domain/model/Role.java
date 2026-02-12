package br.com.atlas.atlas_logistics.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;


import java.util.UUID;

@Entity
@Table(name = "role")
@EqualsAndHashCode
public class Role implements GrantedAuthority{

    @Id
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @Override
    public @Nullable String getAuthority() {
        return this.name;
    }
}
