package br.com.atlas.atlas_logistics.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;


import java.util.UUID;

@Entity
@Table(name = "role")

@Getter
@Setter
public class Role implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @Override
    public @Nullable String getAuthority() {
        return this.name;
    }
}
