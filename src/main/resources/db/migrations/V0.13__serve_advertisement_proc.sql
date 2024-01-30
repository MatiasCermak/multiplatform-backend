DROP
    PROCEDURE IF EXISTS serve_advertisements

GO

CREATE PROCEDURE serve_advertisements(
    @user_id INT,
    @page_type VARCHAR(20)
)
AS
BEGIN
    declare @valid_interests table
                             (
                                 interest_id int
                             )
    declare @valid_advertisements table
                                  (
                                      advertisement_id int,
                                      agency_id        int,
                                      start_date       datetime,
                                      end_date         datetime,
                                      url_image        varchar(200),
                                      file_name        varchar(200),
                                      status           varchar(10),
                                      provided_url     varchar(300),
                                      features         varchar(max)
                                  )
    declare @agg_advertisements table
                                (
                                    advertisement_id int,
                                    agency_id        int,
                                    start_date       datetime,
                                    end_date         datetime,
                                    url_image        varchar(300),
                                    file_name        varchar(200),
                                    status           varchar(10),
                                    provided_url     varchar(300)
                                )

    INSERT INTO @valid_interests
    SELECT interest_id
    FROM interests_users
    where user_id = @user_id
      and active = 1;

    INSERT INTO @valid_advertisements (advertisement_id, agency_id, start_date, end_date, url_image, file_name, status,
                                       provided_url, features)
    SELECT advertisement_id,
           a.agency_id,
           start_date,
           end_date,
           url_image,
           file_name,
           status,
           provided_url,
           ap.features
    FROM advertisements a
             JOIN dbo.agencies a2 on a2.agency_id = a.agency_id
             JOIN dbo.advertisement_plans ap on ap.advertisement_plan_id = a2.advertisement_plan_id
    WHERE a2.active = 1
      AND a.status = 'ACTIVE'
      AND SYSDATETIME() >= a.start_date
      AND (SYSDATETIME() < ISNULL(a.end_date, SYSDATETIME()) OR a.end_date IS NULL)
      AND EXISTS((SELECT value FROM OPENJSON(ap.features, '$.allowed_pages') WHERE value = @page_type));

    -- Insert top priority advertisements with matching interests
    INSERT INTO @agg_advertisements
    SELECT TOP 10000
           advertisement_id,
           agency_id,
           start_date,
           end_date,
           url_image,
           file_name,
           status,
           provided_url
    FROM @valid_advertisements va
    WHERE ISNULL(JSON_VALUE(va.features, '$.priority'), 'LOW') = 'HIGH'
      AND EXISTS(SELECT interest_id
                 FROM advertisements_interests
                 WHERE advertisement_id = va.advertisement_id
                   AND interest_id in (SELECT * FROM @valid_interests))
    ORDER BY NEWID()

    -- Insert top priority advertisements without matching interests
    INSERT INTO @agg_advertisements
    SELECT TOP 10000 advertisement_id,
           agency_id,
           start_date,
           end_date,
           url_image,
           file_name,
           status,
           provided_url
    FROM @valid_advertisements va
    WHERE ISNULL(JSON_VALUE(va.features, '$.priority'), 'LOW') = 'HIGH'
      AND NOT EXISTS(SELECT interest_id
                     FROM advertisements_interests
                     WHERE advertisement_id = va.advertisement_id
                       AND interest_id in (SELECT * FROM @valid_interests))
    ORDER BY NEWID()

    -- Insert low priority advertisements with matching interests
    INSERT INTO @agg_advertisements
    SELECT TOP 10000 advertisement_id,
           agency_id,
           start_date,
           end_date,
           url_image,
           file_name,
           status,
           provided_url
    FROM @valid_advertisements va
    WHERE ISNULL(JSON_VALUE(va.features, '$.priority'), 'LOW') = 'LOW'
      AND EXISTS(SELECT interest_id
                 FROM advertisements_interests
                 WHERE advertisement_id = va.advertisement_id
                   AND interest_id in (SELECT * FROM @valid_interests))
    ORDER BY NEWID()

    -- Insert low priority advertisements without matching interests
    INSERT INTO @agg_advertisements
    SELECT TOP 10000 advertisement_id,
           agency_id,
           start_date,
           end_date,
           url_image,
           file_name,
           status,
           provided_url
    FROM @valid_advertisements va
    WHERE ISNULL(JSON_VALUE(va.features, '$.priority'), 'LOW') = 'LOW'
      AND NOT EXISTS(SELECT interest_id
                     FROM advertisements_interests
                     WHERE advertisement_id = va.advertisement_id
                       AND interest_id in (SELECT * FROM @valid_interests))
    ORDER BY NEWID()

    SELECT * FROM @agg_advertisements
END

GO