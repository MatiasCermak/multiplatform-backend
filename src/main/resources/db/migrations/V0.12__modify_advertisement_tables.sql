ALTER TABLE advertisements DROP COLUMN IF EXISTS file_location
GO

ALTER TABLE advertisements DROP COLUMN IF EXISTS url_location
GO

ALTER TABLE advertisements DROP COLUMN IF EXISTS url_image
GO

ALTER TABLE advertisements ADD url_image VARCHAR(MAX)
GO