-- Insertions in RoomType table
INSERT INTO T_ROOM_TYPE (ROOM_TYPE_NAME, SHARED) VALUES ('Standard Shared', TRUE);
INSERT INTO T_ROOM_TYPE (ROOM_TYPE_NAME, SHARED) VALUES ('Standard Double', FALSE);

-- Insertions in Room table
INSERT INTO T_ROOM (ROOM_NAME, ROOM_TYPE_ID, ROOM_STATUS, OCCUPATION, MAXIMUM_OCCUPATION, MAXIMUM_CHILDREN_OCCUPATION) VALUES ('Room 1', 0, 'FREE', 3, 8, 0);
INSERT INTO T_ROOM (ROOM_NAME, ROOM_TYPE_ID, ROOM_STATUS, OCCUPATION, MAXIMUM_OCCUPATION, MAXIMUM_CHILDREN_OCCUPATION) VALUES ('Room 2', 1, 'OCCUPIED', 1, 2, 1);
INSERT INTO T_ROOM (ROOM_NAME, ROOM_TYPE_ID, ROOM_STATUS, OCCUPATION, MAXIMUM_OCCUPATION, MAXIMUM_CHILDREN_OCCUPATION) VALUES ('Room 3', 0, 'UNCLEAN', 0, 6, 0);
INSERT INTO T_ROOM (ROOM_NAME, ROOM_TYPE_ID, ROOM_STATUS, OCCUPATION, MAXIMUM_OCCUPATION, MAXIMUM_CHILDREN_OCCUPATION) VALUES ('Room 4', 1, 'IN_MAINTENANCE', 0, 2, 1);

-- Insertions in Guest table 
INSERT INTO T_GUEST (GUEST_NAME, DOCUMENT_NUMBER, DOCUMENT_TYPE, BIRTH_DATE) VALUES ('John Doe', '123456789', 'OTHER', '1980-01-01');

-- Insertions in Reserve table
INSERT INTO T_RESERVE (CHECK_IN, CHECK_OUT, ROOM_ID) VALUES ('2000-01-01', '2000-01-02', 0);

-- Insertions in Reserve and Guest join table
INSERT INTO T_RESERVE_GUEST (RESERVE_ID, GUEST_ID) VALUES (0, 0);

-- Insertions in Tariff table
INSERT INTO T_TARIFF (TARIFF_NAME, BEGINNING_DATE, END_DATE, MINIMAL_DAYS, MAXIMUM_DAYS) VALUES ('High Season - > 7 days', '2000-01-01', '2000-02-29', 0, 6);
INSERT INTO T_TARIFF (TARIFF_NAME, BEGINNING_DATE, END_DATE, MINIMAL_DAYS, MAXIMUM_DAYS) VALUES ('High Season - < 7 days', '2000-01-01', '2000-02-29', 7, 90);
INSERT INTO T_TARIFF (TARIFF_NAME, BEGINNING_DATE, END_DATE, MINIMAL_DAYS, MAXIMUM_DAYS) VALUES ('Off-Season - > 7 days', '2000-03-01', '2000-11-30', 0, 6);
INSERT INTO T_TARIFF (TARIFF_NAME, BEGINNING_DATE, END_DATE, MINIMAL_DAYS, MAXIMUM_DAYS) VALUES ('Off-Season - < 7 days', '2000-03-01', '2000-11-30', 7, 90);

-- Insertions in DailyRate table
INSERT INTO T_DAILY_RATE (ROOM_TYPE_ID, TARIFF_ID, BASE_VALUE, INCREASE_PER_GUEST) VALUES (0, 0, 75.00, 75.00);
INSERT INTO T_DAILY_RATE (ROOM_TYPE_ID, TARIFF_ID, BASE_VALUE, INCREASE_PER_GUEST) VALUES (0, 1, 60.00, 60.00);
INSERT INTO T_DAILY_RATE (ROOM_TYPE_ID, TARIFF_ID, BASE_VALUE, INCREASE_PER_GUEST) VALUES (0, 2, 60.00, 60.00);
INSERT INTO T_DAILY_RATE (ROOM_TYPE_ID, TARIFF_ID, BASE_VALUE, INCREASE_PER_GUEST) VALUES (0, 3, 50.00, 50.00);
INSERT INTO T_DAILY_RATE (ROOM_TYPE_ID, TARIFF_ID, BASE_VALUE, INCREASE_PER_GUEST, INCREASE_PER_CHILDREN) VALUES (1, 0, 150.00, 80.00, 50.00);
INSERT INTO T_DAILY_RATE (ROOM_TYPE_ID, TARIFF_ID, BASE_VALUE, INCREASE_PER_GUEST, INCREASE_PER_CHILDREN) VALUES (1, 1, 135.00, 60.00, 40.00);
INSERT INTO T_DAILY_RATE (ROOM_TYPE_ID, TARIFF_ID, BASE_VALUE, INCREASE_PER_GUEST, INCREASE_PER_CHILDREN) VALUES (1, 2, 135.00, 65.00, 40.00);
INSERT INTO T_DAILY_RATE (ROOM_TYPE_ID, TARIFF_ID, BASE_VALUE, INCREASE_PER_GUEST, INCREASE_PER_CHILDREN) VALUES (1, 3, 120.00, 50.00, 35.00);

-- Insertions in Sale table
INSERT INTO T_SALE (CREATED_AT, SALE_STATUS, CHANNEL, TARIFF_ID, TAXES, PARTIAL_PAYMENTS, DAILY_PRICE) VALUES (TIMESTAMP('2024-01-01', '12:00:01'), 'PARTIAL_PAYMENT', 'BOOKING', 0, 50.00, 200.00, 75.00);

-- Insertions in Sale and Reserve join table
INSERT INTO T_SALE_RESERVE (SALE_ID ,RESERVE_ID) VALUES (0, 0);