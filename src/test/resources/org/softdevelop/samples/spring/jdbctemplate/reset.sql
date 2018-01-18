BEGIN;
TRUNCATE product;
INSERT INTO "product" ("id", "name", "price") VALUES (1, 'PC',  130497248);
INSERT INTO "product" ("id", "name", "price") VALUES (2, 'Laptop', 49067981);
INSERT INTO "product" ("id", "name", "price") VALUES (3, 'AP', 46070146);
COMMIT;