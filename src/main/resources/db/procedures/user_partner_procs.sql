DROP
    PROCEDURE IF EXISTS create_user_partner

GO

CREATE PROCEDURE create_user_partner(
    @user_id INT,
    @partner_id INT,
    @transaction_id VARCHAR(MAX)
)
AS
BEGIN
    IF EXISTS(SELECT * FROM partners_users WHERE user_id = @user_id AND partner_id = @partner_id)
        BEGIN
            INSERT INTO partners_users_history (partner_id, user_id, status, type, transaction_id, identity_key, fee,
                                                activation_date, valid_from, valid_to, created_at, updated_at,
                                                deleted_at)
                (SELECT pu.partner_id,
                        pu.user_id,
                        pu.status,
                        pu.type,
                        pu.transaction_id,
                        pu.identity_key,
                        pu.fee,
                        pu.activation_date,
                        pu.activation_date,
                        SYSDATETIME(),
                        pu.created_at,
                        pu.updated_at,
                        pu.deleted_at
                 FROM partners_users pu
                 WHERE user_id = @user_id
                   AND partner_id = @partner_id);

            UPDATE partners_users
            SET deleted_at      = null,
                status          = 'INITIALIZE',
                type            = '',
                transaction_id  = @transaction_id,
                identity_key    = '',
                fee             = 0,
                activation_date = null
            WHERE user_id = @user_id
              AND partner_id = @partner_id;
        END
    ELSE
        BEGIN
            INSERT INTO partners_users (partner_id, user_id, transaction_id, status)
            VALUES (@partner_id, @user_id, @transaction_id, 'INITIALIZE');
        END
    SELECT * FROM partners_users WHERE user_id = @user_id AND partner_id = @partner_id
END
GO

DROP
    PROCEDURE IF EXISTS activate_user_partner
GO

CREATE PROCEDURE activate_user_partner(
    @user_id INT,
    @partner_id INT,
    @identity_key VARCHAR(MAX),
    @type VARCHAR(MAX),
    @fee FLOAT
)
AS
BEGIN
    IF EXISTS(SELECT * FROM partners_users WHERE user_id = @user_id AND partner_id = @partner_id)
        BEGIN
            INSERT INTO partners_users_history (partner_id, user_id, status, type, transaction_id, identity_key, fee,
                                                activation_date, valid_from, valid_to, created_at, updated_at,
                                                deleted_at)
                (SELECT pu.partner_id,
                        pu.user_id,
                        pu.status,
                        pu.type,
                        pu.transaction_id,
                        pu.identity_key,
                        pu.fee,
                        pu.activation_date,
                        pu.activation_date,
                        SYSDATETIME(),
                        pu.created_at,
                        pu.updated_at,
                        pu.deleted_at
                 FROM partners_users pu
                 WHERE user_id = @user_id
                   AND partner_id = @partner_id);

            UPDATE partners_users
            SET deleted_at      = null,
                status          = 'ACTIVE',
                type            = @type,
                identity_key    = @identity_key,
                fee             = @fee,
                activation_date = SYSDATETIME()
            WHERE user_id = @user_id
              AND partner_id = @partner_id;
        END
    ELSE
        BEGIN
            THROW 5002, 'Error not found', 1
        END
    SELECT * FROM partners_users WHERE user_id = @user_id AND partner_id = @partner_id
END
GO

DROP
    PROCEDURE IF EXISTS deactivate_user_partner

GO

CREATE PROCEDURE deactivate_user_partner(
    @user_id INT,
    @partner_id INT
)
AS
BEGIN
    IF EXISTS(SELECT * FROM partners_users WHERE user_id = @user_id AND partner_id = @partner_id)
        BEGIN
            INSERT INTO partners_users_history (partner_id, user_id, status, type, transaction_id, identity_key, fee,
                                                activation_date, valid_from, valid_to, created_at, updated_at,
                                                deleted_at)
                (SELECT pu.partner_id,
                        pu.user_id,
                        pu.status,
                        pu.type,
                        pu.transaction_id,
                        pu.identity_key,
                        pu.fee,
                        pu.activation_date,
                        pu.activation_date,
                        SYSDATETIME(),
                        pu.created_at,
                        pu.updated_at,
                        pu.deleted_at
                 FROM partners_users pu
                 WHERE user_id = @user_id
                   AND partner_id = @partner_id);

            UPDATE partners_users
            SET deleted_at      = null,
                status          = 'INACTIVE',
                type            = '',
                transaction_id  = '',
                identity_key    = '',
                fee             = 0,
                activation_date = null
            WHERE user_id = @user_id
              AND partner_id = @partner_id;
        END
    ELSE
        BEGIN
            THROW 5002, 'Error not found', 1
        END
    SELECT * FROM partners_users WHERE user_id = @user_id AND partner_id = @partner_id
END
GO

DROP
    PROCEDURE IF EXISTS retrieve_user_partner

GO


CREATE PROCEDURE retrieve_user_partner(
    @user_id INT,
    @partner_id INT
)
AS
BEGIN
    SELECT * FROM partners_users WHERE user_id = @user_id AND partner_id = @partner_id
END
GO
DROP
    PROCEDURE IF EXISTS retrieve_user_partner_by_transaction_id

GO


CREATE PROCEDURE retrieve_user_partner_by_transaction_id(
    @transaction_id VARCHAR(MAX)
)
AS
BEGIN
    SELECT * FROM partners_users WHERE transaction_id = @transaction_id
END
GO