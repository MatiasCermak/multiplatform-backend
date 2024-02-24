DROP
    PROCEDURE IF EXISTS list_interests

GO

CREATE PROCEDURE list_interests
AS
BEGIN
    SELECT * from interests WHERE deleted_at IS NULL;
END

GO

DROP
    PROCEDURE IF EXISTS create_interest_user

GO

CREATE PROCEDURE create_interest_user (
    @user_id INT,
    @interest_id INT
)
AS
BEGIN
    INSERT INTO interests_users (interest_id, user_id, active)
    VALUES (@interest_id, @user_id, 1);
    SELECT * FROM interests_users WHERE interest_id = @interest_id AND user_id = @user_id;
END

GO

DROP
    PROCEDURE IF EXISTS create_advertisement_interest

GO

CREATE PROCEDURE create_advertisement_interest (
    @advertisement_id INT,
    @interest_id INT
)
AS
BEGIN
    IF EXISTS(SELECT * FROM advertisements_interests WHERE advertisement_id = @advertisement_id AND interest_id = @interest_id)
        BEGIN
            UPDATE advertisements_interests SET deleted_at = null WHERE interest_id = @interest_id AND advertisement_id = @advertisement_id
        END
    ELSE
        BEGIN
            INSERT INTO advertisements_interests (interest_id, advertisement_id)
            VALUES (@interest_id, @advertisement_id);
        END
    SELECT * FROM advertisements_interests WHERE interest_id = @interest_id AND advertisement_id = @advertisement_id;
END

GO

    DROP
        PROCEDURE IF EXISTS retrieve_advertisement_interests

GO

CREATE PROCEDURE retrieve_advertisement_interests (
    @advertisement_id INT
    )
AS
BEGIN
    SELECT i.interest_id, i.name, i.description, i.created_at, i.updated_at, i.deleted_at FROM advertisements_interests ai JOIN interests i ON ai.interest_id = i.interest_id
             WHERE advertisement_id = @advertisement_id
             AND i.deleted_at IS NULL
             AND ai.deleted_at IS NULL;
END

GO

DROP
    PROCEDURE IF EXISTS delete_advertisement_interest

GO

CREATE PROCEDURE delete_advertisement_interest (
    @advertisement_id INT,
    @interest_id INT
    )
AS
BEGIN
    UPDATE advertisements_interests SET deleted_at = SYSDATETIME() WHERE interest_id = @interest_id AND advertisement_id = @advertisement_id;
    SELECT * FROM advertisements_interests WHERE interest_id = @interest_id AND advertisement_id = @advertisement_id;
END

GO
