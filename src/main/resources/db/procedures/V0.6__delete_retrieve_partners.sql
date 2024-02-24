DROP
    PROCEDURE IF EXISTS delete_partners

GO

CREATE PROCEDURE delete_partners (
    @partner_id INT
)
    AS
BEGIN
    UPDATE partners SET deleted_at = SYSDATETIME(), updated_at = SYSDATETIME() WHERE partner_id = @partner_id;
    SELECT * FROM partners WHERE partner_id = @partner_id;
END

GO

DROP
    PROCEDURE IF EXISTS select_partner

GO

CREATE PROCEDURE select_partner (
    @partner_id INT
)
AS
BEGIN
    SELECT * FROM partners WHERE partner_id = @partner_id;
END

GO