DROP
    PROCEDURE IF EXISTS fetchUser

GO

DROP
    PROCEDURE IF EXISTS get_user_by_email

GO

CREATE PROCEDURE get_user_by_email(
    @email AS VARCHAR(100)
) AS
BEGIN
    declare @valid_partners table
                            (
                                partner_id int
                            )

    INSERT INTO @valid_partners
    SELECT pu.partner_id
    FROM partners_users pu
             JOIN users u ON pu.user_id = u.user_id
            JOIN dbo.partners p on pu.partner_id = p.partner_id
    WHERE u.email = @email
      AND pu.status = 'ACTIVE'
      AND p.active = 1;

    SELECT u.email,
           u.user_id,
           u.name,
           u.last_name,
           u.password,
           r.name as role_name,
           r.role_id,
           STUFF((SELECT ',' + CONVERT(NVARCHAR(10), vp.partner_id)
                  FROM @valid_partners vp
                  FOR xml path('')),
                 1, 1, '') AS partners


    FROM users u
             JOIN dbo.user_roles ur on u.user_id = ur.user_id
             JOIN dbo.roles r on ur.role_id = r.role_id
    WHERE @email = u.email
END

GO

DROP
    PROCEDURE IF EXISTS save_user

GO

DROP
    PROCEDURE IF EXISTS create_user

GO

CREATE PROCEDURE create_user(
    @email AS VARCHAR(100),
    @name AS VARCHAR(100),
    @last_name AS VARCHAR(100),
    @password AS VARCHAR(100),
    @role_id AS INTEGER,
    @agency_id AS INTEGER = NULL,
    @verified_at AS DATETIME = NULL
) AS
BEGIN
    DECLARE @user_id INT;
    IF @verified_at IS NULL
        BEGIN
            SET @verified_at = SYSDATETIME();
        END
    INSERT INTO users (name, last_name, email, username, password,
                       verified_at)
    OUTPUT inserted.user_id
    VALUES (@name, @last_name, @email, @email, @password,
            @verified_at);

    SET @user_id = (SELECT SCOPE_IDENTITY());

    INSERT INTO user_roles (user_id, agency_id, role_id)
    VALUES (@user_id, @agency_id, @role_id);

    SELECT u.email,
           u.user_id,
           u.name,
           u.last_name,
           u.password,
           r.name as role_name,
           r.role_id
    FROM users u
             JOIN dbo.user_roles ur on u.user_id = ur.user_id
             JOIN dbo.roles r on ur.role_id = r.role_id
    WHERE @user_id = u.user_id;
END
