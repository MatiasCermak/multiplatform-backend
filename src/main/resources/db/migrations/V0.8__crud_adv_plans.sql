DROP
    PROCEDURE IF EXISTS create_advertisement_plan

GO

CREATE PROCEDURE create_advertisement_plan(
    @name VARCHAR(50),
    @description VARCHAR(200),
    @features VARCHAR(MAX),
    @price FLOAT
)
AS
BEGIN
    DECLARE @advertisement_plan_id INT;
    INSERT INTO advertisement_plans (name, description, features, price)
    VALUES (@name, @description, @features, @price);
    SET @advertisement_plan_id = (SELECT SCOPE_IDENTITY());
    SELECT * FROM advertisement_plans WHERE advertisement_plan_id = @advertisement_plan_id;
END

GO

DROP
    PROCEDURE IF EXISTS retrieve_advertisement_plan

GO

CREATE PROCEDURE retrieve_advertisement_plan(
    @advertisement_plan_id INT
)
AS
BEGIN
    SELECT * FROM advertisement_plans WHERE advertisement_plan_id = @advertisement_plan_id;
END

GO

DROP
    PROCEDURE IF EXISTS list_advertisement_plans

GO

CREATE PROCEDURE list_advertisement_plans
AS
BEGIN
    SELECT * from advertisement_plans WHERE deleted_at IS NULL;
END

GO

DROP
    PROCEDURE IF EXISTS update_advertisement_plan

GO

CREATE PROCEDURE update_advertisement_plan(
    @advertisement_plan_id INT,
    @name VARCHAR(50),
    @description VARCHAR(200),
    @features VARCHAR(MAX),
    @price FLOAT
)
AS
BEGIN
    UPDATE advertisement_plans
    SET name        = @name,
        description = @description,
        features    = @features,
        price       = @price,
        updated_at  = SYSDATETIME()
    WHERE advertisement_plan_id = @advertisement_plan_id;
    SELECT * FROM advertisement_plans WHERE advertisement_plan_id = @advertisement_plan_id;
END

GO

DROP
    PROCEDURE IF EXISTS delete_advertisement_plan

GO

CREATE PROCEDURE delete_advertisement_plan(
    @advertisement_plan_id INT
)
AS
BEGIN
    UPDATE advertisement_plans
    SET deleted_at = SYSDATETIME(),
        updated_at = SYSDATETIME()
    WHERE advertisement_plan_id = @advertisement_plan_id;
    SELECT * FROM advertisement_plans WHERE advertisement_plan_id = @advertisement_plan_id;
END

GO