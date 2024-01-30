DROP PROCEDURE IF EXISTS serve_content_new_entries

GO

CREATE PROCEDURE serve_content_new_entries(
    @user_id INT
) AS
BEGIN
    declare @valid_partners table
                            (
                                partner_id int
                            )

    INSERT INTO @valid_partners
    SELECT pu.partner_id
    FROM partners_users pu
             JOIN partners p ON pu.partner_id = p.partner_id
    WHERE pu.user_id = @user_id
      AND pu.status = 'ACTIVE'
      AND p.active = 1;

    SELECT DISTINCT c.content_id,
                    eidr_number,
                    name,
                    actors,
                    genre,
                    director,
                    year,
                    image_url,
                    total_views,
                    STUFF((SELECT ',' + CONVERT(NVARCHAR(10), vp.partner_id)
                           FROM @valid_partners vp
                                    JOIN partners_contents pc
                                         ON c.content_id = pc.content_id AND vp.partner_id = pc.partner_id
                           FOR xml path('')),
                          1, 1, '') AS partners
    FROM @valid_partners pv
             JOIN partners_contents pc ON pv.partner_id = pc.partner_id
             JOIN contents c ON pc.content_id = c.content_id
        AND pc.recently_added = 1;
END

GO

DROP PROCEDURE IF EXISTS serve_content_promoted

GO

CREATE PROCEDURE serve_content_promoted(
    @user_id INT
) AS
BEGIN
    declare @valid_partners table
                            (
                                partner_id int
                            )

    INSERT INTO @valid_partners
    SELECT pu.partner_id
    FROM partners_users pu
             JOIN partners p ON pu.partner_id = p.partner_id
    WHERE pu.user_id = @user_id
      AND pu.status = 'ACTIVE'
      AND p.active = 1;

    SELECT DISTINCT c.content_id,
                    eidr_number,
                    name,
                    actors,
                    genre,
                    director,
                    year,
                    image_url,
                    total_views,
                    STUFF((SELECT ',' + CONVERT(NVARCHAR(10), vp.partner_id)
                           FROM @valid_partners vp
                                    JOIN partners_contents pc
                                         ON c.content_id = pc.content_id AND vp.partner_id = pc.partner_id
                           FOR xml path('')),
                          1, 1, '') AS partners
    FROM @valid_partners pv
             JOIN partners_contents pc ON pv.partner_id = pc.partner_id
             JOIN contents c ON pc.content_id = c.content_id
        AND pc.promoted = 1;
END

GO

DROP PROCEDURE IF EXISTS serve_content_most_watched

GO

CREATE PROCEDURE serve_content_most_watched(
    @user_id INT
) AS
BEGIN
    declare @valid_partners table
                            (
                                partner_id int
                            )

    INSERT INTO @valid_partners
    SELECT pu.partner_id
    FROM partners_users pu
             JOIN partners p ON pu.partner_id = p.partner_id
    WHERE pu.user_id = @user_id
      AND pu.status = 'ACTIVE'
      AND p.active = 1;

    SELECT DISTINCT TOP 10 c.content_id,
                           eidr_number,
                           name,
                           actors,
                           genre,
                           director,
                           year,
                           image_url,
                           c.total_views,
                           STUFF((SELECT ',' + CONVERT(NVARCHAR(10), vp.partner_id)
                                  FROM @valid_partners vp
                                           JOIN partners_contents pc
                                                ON c.content_id = pc.content_id AND vp.partner_id = pc.partner_id
                                  FOR xml path('')),
                                 1, 1, '') AS partners
    FROM @valid_partners pv
             JOIN partners_contents pc ON pv.partner_id = pc.partner_id
             JOIN contents c ON pc.content_id = c.content_id
    ORDER BY c.total_views DESC;
END

GO

DROP PROCEDURE IF EXISTS retrieve_content

GO

CREATE PROCEDURE retrieve_content(
    @content_id INT
) AS
BEGIN
    SELECT *,
           STUFF((SELECT ',' + CONVERT(NVARCHAR(10), p.partner_id)
                  FROM partners p
                           JOIN partners_contents pc
                                ON @content_id = pc.content_id AND p.partner_id = pc.partner_id
                  FOR xml path('')),
                 1, 1, '') AS partners
    FROM contents
    WHERE content_id = @content_id;
END

GO

DROP PROCEDURE IF EXISTS filter_content

GO

CREATE PROCEDURE filter_content(
    @query NVARCHAR(MAX),
    @user_id INT
) AS
BEGIN

    DECLARE @sql NVARCHAR(MAX);


    SET @sql = 'declare @valid_partners table
                            (
                                partner_id int
                            )

    INSERT INTO @valid_partners
    SELECT pu.partner_id
    FROM partners_users pu
             JOIN partners p ON pu.partner_id = p.partner_id
    WHERE pu.user_id = ' + (SELECT convert(NVARCHAR,@user_id)) + '
      AND pu.status = ''ACTIVE''
      AND p.active = 1;' +
               'SELECT DISTINCT c.content_id,
                    name,
                    image_url,
                    STUFF((SELECT '','' + CONVERT(NVARCHAR(10), vp.partner_id)
                           FROM @valid_partners vp
                                    JOIN partners_contents pc
                                         ON c.content_id = pc.content_id AND vp.partner_id = pc.partner_id
                           FOR xml path('''')),
                          1, 1, '''') AS partners
    FROM @valid_partners pv
             JOIN partners_contents pc ON pv.partner_id = pc.partner_id
             JOIN contents c ON pc.content_id = c.content_id
        AND ' + @query + ';';

    EXEC sp_sqlexec @sql;
END

GO