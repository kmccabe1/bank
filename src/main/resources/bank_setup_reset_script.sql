DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
    userId INTEGER PRIMARY KEY,
	username TEXT,
	password TEXT
);

INSERT INTO Users (username, password) VALUES
('admin', 1234),
('admin2', 5678);

DROP TABLE IF EXISTS Accounts;

CREATE TABLE Accounts (
    accountId INTEGER PRIMARY KEY,
    accountType TEXT,
    balance DOUBLE(16, 2),
    userId INTEGER,
    FOREIGN KEY(userId) REFERENCES Users(userId)
);

