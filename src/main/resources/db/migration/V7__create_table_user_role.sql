CREATE TABLE user_role(
    id uuid PRIMARY KEY NOT NULL,
    user_id uuid,
    role_id uuid,
    grantedAt TIMESTAMP,
    revokedAt TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    grantedBy VARCHAR(255),

    CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES roles(id)



);