DROP
    PROCEDURE IF EXISTS create_advertisement

GO

CREATE PROCEDURE create_advertisement(
    @agency_id INT,
    @file_name VARCHAR(50),
    @url_image VARCHAR(50),
    @status VARCHAR(10),
    @provided_url VARCHAR(300),
    @start_date DATETIME,
    @end_date DATETIME
)
AS
BEGIN
    DECLARE @advertisement_id INT;
    INSERT INTO advertisements (agency_id, start_date, end_date, url_image, file_name, status, provided_url)
    VALUES (@agency_id, @start_date, @end_date, @url_image, @file_name, @status, @provided_url);
    SET @advertisement_id = (SELECT SCOPE_IDENTITY());
    SELECT * FROM advertisements WHERE advertisement_id = @advertisement_id;
END

GO

DROP
    PROCEDURE IF EXISTS retrieve_advertisement

GO

CREATE PROCEDURE retrieve_advertisement(
    @advertisement_id INT
)
AS
BEGIN
    SELECT * FROM advertisements WHERE advertisement_id = @advertisement_id;
END

GO

DROP
    PROCEDURE IF EXISTS list_advertisements

GO

CREATE PROCEDURE list_advertisements
AS
BEGIN
    SELECT * from advertisements WHERE deleted_at IS NULL;
END

GO

DROP
    PROCEDURE IF EXISTS update_advertisement

GO

CREATE PROCEDURE update_advertisement(
    @advertisement_id INT,
    @agency_id INT,
    @file_name VARCHAR(50),
    @url_image VARCHAR(50),
    @status VARCHAR(10),
    @provided_url VARCHAR(300),
    @start_date DATETIME,
    @end_date DATETIME
)
AS
BEGIN
    UPDATE advertisements
    SET agency_id     = @agency_id,
        file_name     = @file_name,
        url_image = @url_image,
        status        = @status,
        provided_url  = @provided_url,
        start_date    = @start_date,
        end_date      = @end_date,
        updated_at    = SYSDATETIME()
    WHERE advertisement_id = @advertisement_id;
    SELECT * FROM advertisements WHERE advertisement_id = @advertisement_id;
END

GO

DROP
    PROCEDURE IF EXISTS delete_advertisement

GO

CREATE PROCEDURE delete_advertisement(
    @advertisement_id INT
)
AS
BEGIN
    UPDATE advertisements
    SET deleted_at = SYSDATETIME(),
        updated_at = SYSDATETIME()
    WHERE advertisement_id = @advertisement_id;
    SELECT * FROM advertisements WHERE advertisement_id = @advertisement_id;
END

GO