package br.com.atlas.atlas_logistics.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, Serializable {

    @Id
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @Override
    public @Nullable String getAuthority() {
        return this.name;
    }
}
