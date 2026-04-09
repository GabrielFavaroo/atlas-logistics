CREATE TABLE user_role(
    id uuid PRIMARY KEY NOT NULL,
    user_id uuid,
    role_id uuid,
    granted_at TIMESTAMP,
    revoked_at TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    granted_by VARCHAR(255),

    CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES roles(id)



);