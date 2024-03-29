IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'contents' AND COLUMN_NAME = 'image_url')
    BEGIN
        ALTER TABLE contents
            ADD image_url VARCHAR(MAX);
    END;

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'partners' AND COLUMN_NAME = 'image_url')
    BEGIN
        ALTER TABLE partners
            ADD image_url VARCHAR(MAX);
    END;

ALTER TABLE partners DROP COLUMN IF EXISTS advertising_plan_id;