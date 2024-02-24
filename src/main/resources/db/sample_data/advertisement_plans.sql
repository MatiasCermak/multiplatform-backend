SET IDENTITY_INSERT multiplatform.dbo.advertisement_plans ON;
INSERT INTO multiplatform.dbo.advertisement_plans (advertisement_plan_id, name, description, features, price, created_at, updated_at, deleted_at)VALUES (1, N'Basic', N'Basic plan, random publicity in all pages.', N'{
   "allowed_pages":["main"],
   "priority":"LOW"
}', 100, N'2023-10-02 00:52:29.370', null, null);
SET IDENTITY_INSERT multiplatform.dbo.advertisement_plans OFF;
SET IDENTITY_INSERT multiplatform.dbo.advertisement_plans ON;
INSERT INTO multiplatform.dbo.advertisement_plans (advertisement_plan_id, name, description, features, price, created_at, updated_at, deleted_at)VALUES (2, N'Pro', N'Pro plan, priority publicity in all pages.', N'{
   "allowed_pages":["main"],
   "priority":"HIGH"
}', 300, N'2023-10-02 00:52:29.370', null, null);
SET IDENTITY_INSERT multiplatform.dbo.advertisement_plans OFF;
SET IDENTITY_INSERT multiplatform.dbo.advertisement_plans ON;
INSERT INTO multiplatform.dbo.advertisement_plans (advertisement_plan_id, name, description, features, price, created_at, updated_at, deleted_at)VALUES (3, N'Lite', N'Lite Plan, publicity only in the front page.', N'{
   "allowed_pages":["main"],
   "priority":"LOW"
}', 50, N'2023-10-02 00:52:29.370', null, null);
SET IDENTITY_INSERT multiplatform.dbo.advertisement_plans OFF;
