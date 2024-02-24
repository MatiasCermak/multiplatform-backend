DROP PROCEDURE IF EXISTS retrieve_content_by_eidr;

GO

CREATE PROCEDURE retrieve_content_by_eidr(
    @eidr_number VARCHAR(MAX)
)
AS
BEGIN
    SELECT * FROM contents WHERE eidr_number = @eidr_number;
END
GO

DROP PROCEDURE IF EXISTS create_content;

GO

CREATE PROCEDURE create_content(
    @eidr_number VARCHAR(MAX),
    @name VARCHAR(MAX),
    @actors VARCHAR(MAX),
    @genre VARCHAR(MAX),
    @director VARCHAR(MAX),
    @year VARCHAR(MAX),
    @image_url VARCHAR(MAX)
)
AS
BEGIN
    DECLARE @content_id INT;

    INSERT INTO contents (eidr_number, name, actors, genre, director, year, total_views, image_url)
    VALUES (@eidr_number, @name, @actors, @genre, @director, @year, 0, @image_url)

    SET @content_id = (SELECT SCOPE_IDENTITY());

    SELECT * FROM contents WHERE content_id = @content_id;
END
GO

DROP PROCEDURE IF EXISTS dump_content_partners;

GO

CREATE PROCEDURE dump_content_partners
AS
BEGIN

    DECLARE @batch_id INT;

    SET @batch_id = ISNULL((SELECT TOP 1 batch_id FROM partners_contents_history ORDER BY batch_id DESC), 0) + 1;

    INSERT INTO partners_contents_history (batch_id, partner_id, content_id, recently_added, views, promoted)
    SELECT @batch_id, pc.partner_id, pc.content_id, pc.recently_added, pc.views, pc.promoted
    FROM partners_contents pc;

    DELETE FROM partners_contents;
END
GO

DROP PROCEDURE IF EXISTS link_content_to_partner;

GO

CREATE PROCEDURE link_content_to_partner(
    @content_id INT,
    @partner_id INT,
    @recently_added BIT,
    @promoted BIT
)
AS
BEGIN
    INSERT INTO partners_contents (partner_id, content_id, recently_added, views, promoted)
    VALUES (@partner_id, @content_id, @recently_added, 0, @promoted);

    SELECT * FROM partners_contents WHERE partner_id = @partner_id AND content_id = @content_id;
END
GO

-- ****************************************************************************************************************

DROP PROCEDURE IF EXISTS obtain_initialized_advertisements_by_agency_id;

GO

CREATE PROCEDURE obtain_initialized_advertisements_by_agency_id(
    @agency_id INT
)
AS
BEGIN
    SELECT * FROM advertisements a WHERE a.status = 'INIT' AND agency_id = @agency_id;
END
GO

DROP PROCEDURE IF EXISTS activate_advertisement;

GO

CREATE PROCEDURE activate_advertisement(
    @advertisement_id INT,
    @provided_url VARCHAR(MAX),
    @url_image VARCHAR(MAX)
)
AS
BEGIN
    UPDATE advertisements
    SET provided_url = @provided_url,
        url_image    = @url_image,
        status       = 'ACTIVE'
    WHERE advertisement_id = @advertisement_id;

    SELECT * FROM advertisements WHERE advertisement_id = @advertisement_id;
END
GO

-- *****************************************************************************************************************

DROP PROCEDURE IF EXISTS obtain_weekly_clicks_by_agency;

GO

CREATE PROCEDURE obtain_weekly_clicks_by_agency(
    @agency_id INT,
    @week_from DATE,
    @week_to DATE
)
AS
BEGIN
    SELECT a.file_name, c.created_at as clicked_date
    FROM advertisements a
             JOIN clicks c ON a.advertisement_id = c.advertisement_id
    WHERE agency_id = @agency_id
      and c.created_at BETWEEN @week_from AND @week_to;
END
GO

-- *****************************************************************************************************************

DROP PROCEDURE IF EXISTS obtain_weekly_clicks_by_partner;

GO

CREATE PROCEDURE obtain_weekly_clicks_by_partner(
    @partner_id INT,
    @week_from DATE,
    @week_to DATE
)
AS
BEGIN
    SELECT co.eidr_number, c.created_at as clicked_date
    FROM contents co
             JOIN clicks c ON co.content_id = c.content_id
    WHERE EXISTS(SELECT pc.content_id FROM partners_contents pc WHERE pc.partner_id = @partner_id AND pc.content_id = co.content_id)
      and c.created_at BETWEEN @week_from AND @week_to;
END
GO

-- *****************************************************************************************************************

DROP PROCEDURE IF EXISTS obtain_partner_balance;

GO

CREATE PROCEDURE obtain_partner_balance(
    @partner_id INT,
    @day_from DATE,
    @day_to DATE
)
AS
BEGIN

    DECLARE @register_sum FLOAT = 0;
    DECLARE @login_sum FLOAT = 0;

    SET @register_sum = ISNULL((SELECT SUM(ISNULL(fee, 0)) FROM partners_users WHERE type = 'REGISTER' AND partner_id = @partner_id AND CONVERT(DATE, activation_date) BETWEEN @day_from AND @day_to), 0);
    SET @login_sum = ISNULL((SELECT SUM(ISNULL(fee, 0)) FROM partners_users WHERE type = 'LOGIN' AND partner_id = @partner_id AND CONVERT(DATE, activation_date) BETWEEN @day_from AND @day_to), 0);

    SET @register_sum = @register_sum + ISNULL((SELECT SUM(ISNULL(fee, 0)) FROM partners_users_history WHERE type = 'REGISTER' AND partner_id = @partner_id AND CONVERT(DATE, activation_date) BETWEEN @day_from AND @day_to), 0);
    SET @login_sum = @login_sum + ISNULL((SELECT SUM(ISNULL(fee, 0)) FROM partners_users_history WHERE type = 'LOGIN' AND partner_id = @partner_id AND CONVERT(DATE, activation_date) BETWEEN @day_from AND @day_to), 0);

    SELECT @register_sum as register_amount, @login_sum as login_amount, (@login_sum - @register_sum) as owed_amount;
END
GO