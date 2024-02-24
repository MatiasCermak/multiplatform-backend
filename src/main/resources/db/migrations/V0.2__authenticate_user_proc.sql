--liquibase formatted sql

--endDelimiter:GO
CREATE TABLE [roles]
(
    role_id     INT IDENTITY NOT NULL,
    name        VARCHAR(10)  NOT NULL,
    description VARCHAR(300),
    CONSTRAINT PK__roles__END PRIMARY KEY (role_id),
    CONSTRAINT UQ__roles__name__END UNIQUE (name)
);

INSERT INTO roles (name, description)
VALUES
    ('ADMIN', 'System admin user role'),
    ('AGENCY', 'Agency admin user role'),
    ('USER', 'Multiplatform client user role');

ALTER TABLE user_roles
    ADD role_id INT NOT NULL;
ALTER TABLE user_roles
    ADD CONSTRAINT FK__user_roles__roles__END FOREIGN KEY (role_id) REFERENCES roles (role_id);
ALTER TABLE user_roles
    DROP COLUMN type;

GO