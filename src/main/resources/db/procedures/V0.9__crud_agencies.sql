DROP
    PROCEDURE IF EXISTS create_agency

GO

CREATE PROCEDURE create_agency(
    @name VARCHAR(50),
    @protocol VARCHAR(4),
    @url_service VARCHAR(MAX),
    @secret_token VARCHAR(50),
    @active BIT,
    @advertisement_plan_id INT
)
AS
BEGIN
    DECLARE @agency_id INT;
    INSERT INTO agencies (name, protocol, url_service, secret_token, active, advertisement_plan_id)
    VALUES (@name, @protocol, @url_service, @secret_token, @active, @advertisement_plan_id);
    SET @agency_id = (SELECT SCOPE_IDENTITY());
    SELECT * FROM agencies WHERE agency_id = @agency_id;
END

GO

DROP
    PROCEDURE IF EXISTS retrieve_agency

GO

CREATE PROCEDURE retrieve_agency(
    @agency_id INT
)
AS
BEGIN
    SELECT * FROM agencies WHERE agency_id = @agency_id;
END

GO

DROP
    PROCEDURE IF EXISTS list_agencies

GO

CREATE PROCEDURE list_agencies
AS
BEGIN
    SELECT * from agencies WHERE deleted_at IS NULL;
END

GO

DROP
    PROCEDURE IF EXISTS update_agency

GO

CREATE PROCEDURE update_agency(
    @agency_id INT,
    @name VARCHAR(50),
    @protocol VARCHAR(4),
    @url_service VARCHAR(300),
    @secret_token VARCHAR(50),
    @active BIT,
    @advertisement_plan_id INT
)
AS
BEGIN
    UPDATE agencies
    SET name                    = @name,
        protocol                = @protocol,
        url_service             = @url_service,
        secret_token            = @secret_token,
        active                  = @active,
        advertisement_plan_id   = @advertisement_plan_id,
        updated_at              = SYSDATETIME()
    WHERE agency_id = @agency_id;
    SELECT * FROM agencies WHERE agency_id = @agency_id;
END

GO

DROP
    PROCEDURE IF EXISTS delete_agency

GO

CREATE PROCEDURE delete_agency(
    @agency_id INT
)
AS
BEGIN
    UPDATE agencies
    SET deleted_at = SYSDATETIME(),
        updated_at = SYSDATETIME()
    WHERE agency_id = @agency_id;
    SELECT * FROM agencies WHERE agency_id = @agency_id;
END

GO