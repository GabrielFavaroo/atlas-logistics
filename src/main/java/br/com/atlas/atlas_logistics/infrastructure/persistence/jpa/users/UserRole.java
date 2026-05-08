package br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "granted_at")
    private LocalDateTime grantedAt;
    @Column(name = "revoked_at",nullable = true)
    private LocalDateTime revokedAt;
    @Column(name = "granted_by")
    private String grantedBy;

    public UserRole( User user, Role role, Boolean active, LocalDateTime grantedAt, LocalDateTime revokedAt, String grantedBy) {
        this.active = active;
        this.grantedAt = grantedAt;
        this.user = user;
        this.role = role;
        this.revokedAt = revokedAt;
        this.grantedBy = grantedBy;
    }
}
