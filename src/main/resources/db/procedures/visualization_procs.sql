DROP
    PROCEDURE IF EXISTS create_visualization

GO

CREATE PROCEDURE create_visualization(
    @user_id INT,
    @partner_id INT,
    @content_id INT
)
AS
BEGIN
    DECLARE @visualization_id INT;

    INSERT INTO visualizations (content_id, user_id, partner_id) VALUES (@content_id, @user_id, @partner_id);

    SET @visualization_id = (SELECT SCOPE_IDENTITY());

    UPDATE partners_contents SET views = views + 1 WHERE content_id = @content_id AND partner_id = @partner_id;
    UPDATE contents SET total_views = total_views + 1 WHERE content_id = @content_id;

    SELECT * FROM visualizations WHERE visualization_id = @visualization_id;
END
GO