-- SQL schema for Guest, Reserve, Room, Room Type and Tariff entities

-- Table for Guest entity
CREATE TABLE T_GUEST (
    ID BIGINT IDENTITY PRIMARY KEY,
    GUEST_NAME VARCHAR(255),
    DOCUMENT_NUMBER VARCHAR(255),
    DOCUMENT_TYPE VARCHAR(50),
    BIRTH_DATE DATE,
    EMAIL VARCHAR(255),
    TELEPHONE VARCHAR(255),
    HOME_ADDRESS VARCHAR(255)
);

-- Table for Tariff entity
CREATE TABLE T_TARIFF (
    ID BIGINT IDENTITY PRIMARY KEY,
    TARIFF_NAME VARCHAR(255),
    BEGINNING_DATE DATE,
    END_DATE DATE,
    MINIMAL_DAYS INT,
    MAXIMUM_DAYS INT
);

-- Table for RoomType entity
CREATE TABLE T_ROOM_TYPE (
    ID BIGINT IDENTITY PRIMARY KEY, -- AUTO_INCREMENT for IDENTITY generation strategy
    ROOM_TYPE_NAME VARCHAR(255) UNIQUE, -- Unique constraint for room type name
    ROOM_TYPE_DESCRIPTION VARCHAR(255),
    SHARED BOOLEAN
);

-- Table for DailyRate entity
CREATE TABLE T_DAILY_RATE (
    ID BIGINT IDENTITY PRIMARY KEY,
    ROOM_TYPE_ID BIGINT,
    TARIFF_ID BIGINT,
    BASE_VALUE DECIMAL(10, 2),
    INCREASE_PER_GUEST DECIMAL(10, 2),
    INCREASE_PER_CHILDREN DECIMAL(10, 2),
    FOREIGN KEY (ROOM_TYPE_ID) REFERENCES T_ROOM_TYPE(ID),
    FOREIGN KEY (TARIFF_ID) REFERENCES T_TARIFF(ID)
);

-- Table for Room entity
CREATE TABLE T_ROOM (
    ID BIGINT IDENTITY PRIMARY KEY, -- AUTO_INCREMENT used for IDENTITY generation strategy
    ROOM_NAME VARCHAR(255),
    ROOM_TYPE_ID BIGINT, -- Foreign key for RoomType
    ROOM_STATUS VARCHAR(50), -- Enum values for RoomStatus
    OCCUPATION INT,
    MAXIMUM_OCCUPATION INT,
    MAXIMUM_CHILDREN_OCCUPATION INT,
    FOREIGN KEY (ROOM_TYPE_ID) REFERENCES T_ROOM_TYPE(ID) 
);

-- Table for Reserve entity
CREATE TABLE T_RESERVE (
    ID BIGINT IDENTITY PRIMARY KEY,
    CHECK_IN DATE,
    CHECK_OUT DATE,
    ROOM_ID BIGINT,
    FOREIGN KEY (ROOM_ID) REFERENCES T_ROOM(ID) 
);

-- Join table for Reserve and Guest entities
CREATE TABLE T_RESERVE_GUEST (
    RESERVE_ID BIGINT,
    GUEST_ID BIGINT,
    PRIMARY KEY (RESERVE_ID, GUEST_ID),
    FOREIGN KEY (RESERVE_ID) REFERENCES T_RESERVE(ID),
    FOREIGN KEY (GUEST_ID) REFERENCES T_GUEST(ID)
);

-- Table for Sale entity
CREATE TABLE T_SALE (
    ID BIGINT IDENTITY PRIMARY KEY,
    CREATED_AT TIMESTAMP,
    SALE_STATUS VARCHAR(50),
    CHANNEL VARCHAR(50),
    CHANNEL_ID VARCHAR(50),
    TARIFF_ID BIGINT,
    TAXES DECIMAL(10, 2),
    PARTIAL_PAYMENTS DECIMAL(10, 2),
    DISCOUNTS DECIMAL(10, 2),
    DAILY_PRICE DECIMAL(10, 2),
    OBSERVATIONS VARCHAR(255),
    FOREIGN KEY (TARIFF_ID) REFERENCES T_TARIFF(ID)
);

--Join table for Sale and Reserve entities
CREATE TABLE T_SALE_RESERVE (
    SALE_ID BIGINT,
    RESERVE_ID BIGINT,
    PRIMARY KEY (SALE_ID, RESERVE_ID),
    FOREIGN KEY (SALE_ID) REFERENCES T_SALE(ID),
    FOREIGN KEY (RESERVE_ID) REFERENCES T_RESERVE
)