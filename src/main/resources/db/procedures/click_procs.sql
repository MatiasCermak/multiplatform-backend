DROP PROCEDURE IF EXISTS  create_click;

GO

CREATE PROCEDURE create_click(
    @user_id INT,
    @advertisement_id INT = NULL,
    @content_id INT = NULL,
    @type VARCHAR(15)
)
    AS
BEGIN
    DECLARE @click_id INT;

    INSERT INTO clicks (user_id, advertisement_id, content_id, type) VALUES (@user_id, @advertisement_id, @content_id, @type);

    SET @click_id = (SELECT SCOPE_IDENTITY());

    SELECT * FROM clicks WHERE click_id = @click_id;
END
GO