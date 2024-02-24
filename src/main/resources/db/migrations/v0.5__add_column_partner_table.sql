IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'partners' AND COLUMN_NAME = 'federation_page')
    BEGIN
        ALTER TABLE partners
            ADD federation_page VARCHAR(MAX);
    END;