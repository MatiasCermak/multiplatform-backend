drop table if exists advertisement_plan_history
go

drop table if exists advertisement_plan_price_history
go

drop table if exists advertisements_interests
go

drop table if exists advertisements
go

drop table if exists clicks
go

drop table if exists configurations
go

drop table if exists interests_users
go

drop table if exists interests
go

drop table if exists partner_fee_price_history
go

drop table if exists partners_contents
go

drop table if exists partners_contents_history
go

drop table if exists partners_users
go

drop table if exists partners_users_history
go

drop table if exists user_roles
go

drop table if exists agencies
go

drop table if exists advertisement_plans
go

drop table if exists roles
go

drop table if exists visualizations
go

drop table if exists contents
go

drop table if exists partners
go

drop table if exists users
go


CREATE TABLE [users]
(
    [user_id]     INT IDENTITY NOT NULL,
    [name]        VARCHAR(100) NOT NULL,
    [last_name]   VARCHAR(100) NOT NULL,
    [email]       VARCHAR(100) NOT NULL,
    [username]    VARCHAR(100) NOT NULL,
    [password]    VARCHAR(100) NOT NULL,
    [verified_at] DATETIME,
    [created_at]  DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]  DATETIME,
    [deleted_at]  DATETIME,
    CONSTRAINT PK__users__END PRIMARY KEY (user_id)
);

CREATE TABLE [partners]
(
    [partner_id]          INT IDENTITY NOT NULL,
    [name]                VARCHAR(100) NOT NULL,
    [protocol]           VARCHAR(4)   NOT NULL,
    [url_service]         VARCHAR(250) NOT NULL,
    [login_fee]           FLOAT        NOT NULL,
    [register_fee]        FLOAT        NOT NULL,
    [secret_token]        VARCHAR(100) NOT NULL,
    [active]              BIT          NOT NULL,
    [advertising_plan_id] INT          NOT NULL,
    [created_at]          DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]          DATETIME,
    [deleted_at]          DATETIME,
    CONSTRAINT PK__partners__END PRIMARY KEY (partner_id)
);

CREATE TABLE [partners_users_history]
(
    [partners_users_history_id] INT IDENTITY NOT NULL,
    [partner_id]                INT          NOT NULL,
    [user_id]                   INT          NOT NULL,
    [status]                    VARCHAR(10)  NOT NULL,
    [type]                      VARCHAR(10)  NOT NULL,
    [transaction_id]            VARCHAR(50),
    [identity_key]              VARCHAR(100),
    [fee]                       FLOAT        NOT NULL,
    [activation_date]           DATETIME,
    [valid_from]                DATETIME     NOT NULL,
    [valid_to]                  DATETIME,
    [created_at]                DATETIME DEFAULT (SYSDATETIME()),
    [updated_at]                DATETIME,
    [deleted_at]                DATETIME,
    CONSTRAINT PK__partners_users_history__END PRIMARY KEY (partners_users_history_id),
    CONSTRAINT FK__partners_users_history__users__END FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK__partners_users_history__partners__END FOREIGN KEY (partner_id) REFERENCES partners (partner_id),
    CONSTRAINT UQ__partners_users_history__transaction_id__END UNIQUE (transaction_id)
);

CREATE TABLE [contents]
(
    [content_id]  INT IDENTITY NOT NULL,
    [eidr_number] VARCHAR(26)  NOT NULL,
    [name]        VARCHAR(100) NOT NULL,
    [actors]      VARCHAR(300) NOT NULL,
    [genre]       VARCHAR(20)  NOT NULL,
    [director]    VARCHAR(100) NOT NULL,
    [year]        VARCHAR(4)   NOT NULL,
    [total_views] INT          NOT NULL DEFAULT (0),
    [created_at]  DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]  DATETIME,
    CONSTRAINT PK__contents__END PRIMARY KEY (content_id),
    CONSTRAINT UQ__contents__eidr_number__END UNIQUE (eidr_number)
);

CREATE TABLE [partners_contents]
(
    [partner_id]     INT NOT NULL,
    [content_id]     INT NOT NULL,
    [recently_added] BIT NOT NULL DEFAULT (0),
    [views]          INT NOT NULL DEFAULT (0),
    [promoted]       BIT NOT NULL DEFAULT (0),
    [created_at]     DATETIME     DEFAULT (SYSDATETIME()),
    CONSTRAINT PK__partner_contents__END PRIMARY KEY (partner_id, content_id),
    CONSTRAINT FK__partner_contents__partners__END FOREIGN KEY (partner_id) REFERENCES partners (partner_id),
    CONSTRAINT FK__partner_contents__contents__END FOREIGN KEY (content_id) REFERENCES contents (content_id)
);

CREATE TABLE [configurations]
(
    [configuration_id] INT IDENTITY NOT NULL,
    [name]             VARCHAR(100),
    [value]            VARCHAR(MAX),
    [type]             VARCHAR(10)  NOT NULL DEFAULT ('TEXT'),
    [created_at]       DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]       DATETIME,
    [deleted_at]       DATETIME,
    CONSTRAINT PK__configurations__END PRIMARY KEY (configuration_id),
    CONSTRAINT UQ__configurations__name__END UNIQUE (name)
);

CREATE TABLE [visualizations]
(
    [visualization_id] INT IDENTITY NOT NULL,
    [content_id]       INT          NOT NULL,
    [user_id]          INT          NOT NULL,
    [partner_id]       INT          NOT NULL,
    [created_at]       DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]       DATETIME,
    [deleted_at]       DATETIME,
    CONSTRAINT PK__visualizations__END PRIMARY KEY (visualization_id),
    CONSTRAINT FK__visualizations__users__END FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK__visualizations__partners__END FOREIGN KEY (partner_id) REFERENCES partners (partner_id),
    CONSTRAINT FK__visualizations__contents__END FOREIGN KEY (content_id) REFERENCES contents (content_id),
);

CREATE TABLE [advertisement_plans]
(
    [advertisement_plan_id] INT IDENTITY NOT NULL,
    [name]                  VARCHAR(50)  NOT NULL,
    [description]           VARCHAR(200),
    [features]              VARCHAR(MAX) NOT NULL,
    [price]                 FLOAT        NOT NULL,
    [created_at]            DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]            DATETIME,
    [deleted_at]            DATETIME,
    CONSTRAINT PK__advertising_plans__END PRIMARY KEY (advertisement_plan_id)
);

CREATE TABLE [agencies]
(
    [agency_id]             INT IDENTITY NOT NULL,
    [name]                VARCHAR(20),
    [protocol]             VARCHAR(4),
    [url_service]           VARCHAR(300),
    [secret_token]          VARCHAR(50),
    [active]                BIT          NOT NULL DEFAULT (0),
    [advertisement_plan_id] INT          NOT NULL,
    [created_at]            DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]            DATETIME,
    [deleted_at]            DATETIME,
    CONSTRAINT PK__agencies__END PRIMARY KEY (agency_id),
    CONSTRAINT FK__agencies__advertisement_plan__END FOREIGN KEY (advertisement_plan_id) REFERENCES advertisement_plans (advertisement_plan_id)
);

CREATE TABLE [advertisements]
(
    [advertisement_id] INT IDENTITY NOT NULL,
    [agency_id]        INT          NOT NULL,
    [start_date]       DATETIME     NOT NULL,
    [end_date]         DATETIME,
    [file_location]    VARCHAR(200),
    [file_name]        VARCHAR(200),
    [status]           VARCHAR(10),
    [provided_url]     VARCHAR(300),
    [created_at]       DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]       DATETIME,
    [deleted_at]       DATETIME,
    CONSTRAINT PK__advertisements__END PRIMARY KEY (advertisement_id),
    CONSTRAINT FK__advertisements__agencies__END FOREIGN KEY (agency_id) REFERENCES agencies (agency_id)
);

CREATE TABLE [clicks]
(
    [click_id]         INT IDENTITY NOT NULL,
    [user_id]          INT          NOT NULL,
    [advertisement_id] INT,
    [type]             VARCHAR(6),
    [click_metadata]   VARCHAR(MAX),
    [date_time]        DATETIME     NOT NULL,
    [created_at]       DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]       DATETIME,
    [deleted_at]       DATETIME,
    CONSTRAINT PK__clicks__END PRIMARY KEY (click_id),
    CONSTRAINT FK__clicks__users__END FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE [interests]
(
    [interest_id] INT IDENTITY NOT NULL,
    [name]        VARCHAR(50)  NOT NULL,
    [description] VARCHAR(200),
    [created_at]  DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]  DATETIME,
    [deleted_at]  DATETIME,
    CONSTRAINT PK__interests__END PRIMARY KEY (interest_id)
);

CREATE TABLE [interests_users]
(
    [interest_id] INT      NOT NULL,
    [user_id]     INT      NOT NULL,
    [active]      BIT      NOT NULL DEFAULT (1),
    [created_at]  DATETIME NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]  DATETIME,
    [deleted_at]  DATETIME,
    CONSTRAINT FK__interest_users__interest__END FOREIGN KEY (interest_id) REFERENCES interests (interest_id),
    CONSTRAINT FK__interest_users__users__END FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE [advertisement_plan_price_history]
(
    [advertisement_plan_price_history_id] INT IDENTITY NOT NULL,
    [advertising_plan_id]                 INT          NOT NULL,
    [valid_from]                          DATE         NOT NULL,
    [valid_to]                            DATE         NOT NULL,
    [price]                               FLOAT,
    [created_at]                          DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]                          DATETIME,
    [deleted_at]                          DATETIME,
    CONSTRAINT PK__advertisement_plan_price_history__END PRIMARY KEY (advertisement_plan_price_history_id),
    CONSTRAINT FK__advertisement_plan_price_history__advertising_plans__END FOREIGN KEY (advertising_plan_id) REFERENCES advertisement_plans (advertisement_plan_id)
);

CREATE TABLE [user_roles]
(
    [user_roles_id] INT IDENTITY NOT NULL,
    [user_id]       INT          NOT NULL,
    [agency_id]     INT,
    [type]          VARCHAR(5),
    [created_at]    DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]    DATETIME,
    [deleted_at]    DATETIME,
    CONSTRAINT PK__user_roles__END PRIMARY KEY (user_roles_id),
    CONSTRAINT FK__user_roles__users__END FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK__user_roles__agencies__END FOREIGN KEY (agency_id) REFERENCES agencies (agency_id)
);

CREATE TABLE [partners_users]
(
    [partner_id]      INT      NOT NULL,
    [user_id]         INT      NOT NULL,
    [status]          VARCHAR(10),
    [type]            VARCHAR(10),
    [fee]             FLOAT,
    [transaction_id]  VARCHAR(50),
    [identity_key]    VARCHAR(50),
    [activation_date] DATETIME,
    [created_at]      DATETIME NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]      DATETIME,
    [deleted_at]      DATETIME,
    CONSTRAINT FK__partners_users__partners__END FOREIGN KEY (partner_id) REFERENCES partners (partner_id),
    CONSTRAINT FK__partners_users__users__END FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE [advertisement_plan_history]
(
    [advertisement_plan_history_id] INT IDENTITY NOT NULL,
    [agency_id]                     INT          NOT NULL,
    [advertisement_plan_id]         INT          NOT NULL,
    [adv_price_history_id]          INT          NOT NULL,
    [valid_from]                    DATETIME     NOT NULL,
    [valid_to]                      DATETIME     NOT NULL,
    [created_at]                    DATETIME     NOT NULL,
    [updated_at]                    DATETIME,
    [deleted_at]                    DATETIME,
    CONSTRAINT PK__advertisement_plan_history__END PRIMARY KEY (advertisement_plan_history_id),
    CONSTRAINT FK__advertisement_plan_history__agencies__END FOREIGN KEY (agency_id) REFERENCES agencies (agency_id),
    CONSTRAINT FK__advertisement_plan_history__advertisement_plans__END FOREIGN KEY (advertisement_plan_id) REFERENCES advertisement_plans (advertisement_plan_id),
);

CREATE TABLE [advertisements_interests]
(
    [interest_id]      INT      NOT NULL,
    [advertisement_id] INT      NOT NULL,
    [status]           BIT      NOT NULL DEFAULT (1),
    [created_at]       DATETIME NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]       DATETIME,
    [deleted_at]       DATETIME,
    CONSTRAINT FK__advertisements_interests__interests__END FOREIGN KEY (interest_id) REFERENCES interests (interest_id),
    CONSTRAINT FK__advertisements_interests__advertisements__END FOREIGN KEY (advertisement_id) REFERENCES advertisements (advertisement_id)
);

CREATE TABLE [partner_fee_price_history]
(
    [partner_fee_price_history_id] INT IDENTITY NOT NULL,
    [partner_id]                   INT          NOT NULL,
    [login_fee]                    FLOAT        NOT NULL,
    [register_fee]                 FLOAT        NOT NULL,
    [valid_from]                   DATETIME     NOT NULL,
    [valid_to]                     DATETIME     NOT NULL,
    [created_at]                   DATETIME     NOT NULL DEFAULT (SYSDATETIME()),
    [updated_at]                   DATETIME,
    [deleted_at]                   DATETIME,
    CONSTRAINT PK__partner_fee_price_history__END PRIMARY KEY (partner_fee_price_history_id),
    CONSTRAINT FK__partner_fee_price_history__partners__END FOREIGN KEY (partner_id) REFERENCES partners (partner_id)
);

CREATE TABLE [partners_contents_history]
(
    [partners_contents_history_id] INT IDENTITY NOT NULL,
    [batch_id]                     INT          NOT NULL,
    [partner_id]                   INT          NOT NULL,
    [content_id]                   INT          NOT NULL,
    [recently_added]               BIT          NOT NULL DEFAULT (0),
    [views]                        INT          NOT NULL DEFAULT (0),
    [promoted]                     BIT          NOT NULL DEFAULT (0),
    [created_at]                   DATETIME     NOT NULL,
    CONSTRAINT PK__partners_contents_history__END PRIMARY KEY (partners_contents_history_id),
    CONSTRAINT FK__partners_contents_history__partners__END FOREIGN KEY (partner_id) REFERENCES partners (partner_id),
    CONSTRAINT FK__partners_contents_history__contents__END FOREIGN KEY (content_id) REFERENCES contents (content_id)
);