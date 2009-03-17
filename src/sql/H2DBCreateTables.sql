-- ---------- Table for validation queries from the connection pool -----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- -----------------------------------------------------------------------------
-- Drop tables. NOTE: before dropping a table (when re-executing the script),
-- the tables having columns acting as foreign keys of the table to be dropped,
-- must be dropped first (otherwise, the corresponding checks on those tables
-- could not be done).

DROP TABLE AccountOp;
DROP TABLE Category;
DROP TABLE Account;
DROP TABLE User;

-- Users table

CREATE TABLE User (
	usrId BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	version BIGINT,
	CONSTRAINT UserPK PRIMARY KEY (usrId)
);

-- Accounts table
CREATE TABLE Account (
	accId BIGINT NOT NULL AUTO_INCREMENT,
	usrId BIGINT NOT NULL,
	name VARCHAR(100) NOT NULL,
	balance DOUBLE PRECISION NOT NULL,
	version BIGINT,
	CONSTRAINT AccountPK PRIMARY KEY (accId),
	CONSTRAINT AccontUserIdFK FOREIGN KEY (usrId) REFERENCES User(usrId)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX AccountIndexByAccId ON Account(accId);
CREATE INDEX AccountIndexByUsrId ON Account(accId,usrId);

-- Category table
CREATE TABLE Category (
	categoryId BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	parentCategoryId BIGINT,
	version BIGINT,
	CONSTRAINT CategoryPK PRIMARY KEY (categoryId),
	CONSTRAINT CategoryFK FOREIGN KEY(parentCategoryId) REFERENCES Category (categoryId)
		ON UPDATE CASCADE ON DELETE SET NULL 
);



-- Account Operations table
CREATE TABLE AccountOp (
	accOpId BIGINT NOT NULL AUTO_INCREMENT,
	accId BIGINT NOT NULL,
	type TINYINT NOT NULL,
	amount DECIMAL NOT NULL,
	comment VARCHAR(100),
	date TIMESTAMP NOT NULL,
	categoryId BIGINT,
	version BIGINT,
	CONSTRAINT AccountOpPK PRIMARY KEY (accOpId),
	CONSTRAINT AccountIdFK FOREIGN KEY (accId) REFERENCES Account(accId) 
		ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT AccountCategoryFK FOREIGN KEY (categoryId) REFERENCES Category(categoryId)
		ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT validType CHECK (type >=0 AND type <= 1),
	CONSTRAINT validAmount CHECK (amount>0) 
);

CREATE INDEX AccountOpIndexByAccOpId ON AccountOp(accOpId);
CREATE INDEX AccountOpIndexByAccId ON AccountOp(accOpId,accId);
CREATE INDEX AccountOpIndexByOpTypeId ON AccountOp(accOpId,accId,type);
CREATE INDEX AccountOpIndexByDate ON AccountOp(accOpId,accId,date);