DROP
    PROCEDURE IF EXISTS list_partners

GO

CREATE PROCEDURE list_partners
AS
BEGIN
    SELECT * from partners WHERE deleted_at IS NULL;
END

GO