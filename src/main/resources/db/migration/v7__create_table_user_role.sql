CREATE TABLE user_role(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID,
    role_id UUID,
    grantedAt TIMESTAMP,
    revokedAt TIMESTAMP
    active BOOLEAN DEFAULT TRUE,
    grantedBy VARCHAR(255)

    CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES role(id)



)