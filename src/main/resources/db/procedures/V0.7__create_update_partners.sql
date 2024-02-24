DROP
    PROCEDURE IF EXISTS create_partner

GO

CREATE PROCEDURE create_partner(
    @name VARCHAR(100),
    @protocol VARCHAR(4),
    @url_service VARCHAR(250),
    @login_fee FLOAT,
    @register_fee FLOAT,
    @secret_token VARCHAR(100),
    @active BIT,
    @image_url VARCHAR(MAX)
)
AS
BEGIN
    DECLARE @partner_id INT;
    INSERT INTO partners (name, protocol, url_service, login_fee, register_fee, secret_token, active, image_url)
    VALUES (@name, @protocol, @url_service, @login_fee, @register_fee, @secret_token, @active, @image_url);
    SET @partner_id = (SELECT SCOPE_IDENTITY());
    SELECT * FROM partners WHERE partner_id = @partner_id;
END

GO

DROP
    PROCEDURE IF EXISTS update_partner

GO

CREATE PROCEDURE update_partner(
    @partner_id INT,
    @name VARCHAR(100),
    @protocol VARCHAR(4),
    @url_service VARCHAR(250),
    @login_fee FLOAT,
    @register_fee FLOAT,
    @secret_token VARCHAR(100),
    @active BIT,
    @image_url VARCHAR(MAX)
)
AS
BEGIN
    UPDATE partners
    SET name         = @name,
        protocol     = @protocol,
        url_service  = @url_service,
        login_fee    = @login_fee,
        register_fee = @register_fee,
        secret_token = @secret_token,
        active       = @active,
        updated_at   = SYSDATETIME(),
        image_url    = @image_url
    WHERE partner_id = @partner_id;
    SELECT * FROM partners WHERE partner_id = @partner_id;
END

GO