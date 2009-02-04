-- ---------- Table for validation queries from the connection pool -----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- -----------------------------------------------------------------------------
-- Drop tables. NOTE: before dropping a table (when re-executing the script),
-- the tables having columns acting as foreign keys of the table to be dropped,
-- must be dropped first (otherwise, the corresponding checks on those tables
-- could not be done).

DROP TABLE OpCategory;
DROP TABLE Category;
DROP TABLE AccountOp;
DROP TABLE Account;
DROP TABLE User;

-- Users table

CREATE TABLE User (
	usrId BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	version BIGINT,
	CONSTRAINT UserPK PRIMARY KEY (usrId)
) TYPE InnoDB;

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
) TYPE InnoDB;

CREATE INDEX AccountIndexByAccId ON Account(accId);
CREATE INDEX AccountIndexByUsrId ON Account(accId,usrId);

-- Account Operations table
CREATE TABLE AccountOp (
	accOpId BIGINT NOT NULL AUTO_INCREMENT,
	accId BIGINT NOT NULL,
	type TINYINT NOT NULL,
	amount DOUBLE PRECISION NOT NULL,
	comment VARCHAR(100),
	date TIMESTAMP NOT NULL,
	version BIGINT,
	CONSTRAINT AccountOpPK PRIMARY KEY (accOpId),
	CONSTRAINT AccountIdFK FOREIGN KEY (accId) REFERENCES Account(accId) 
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT validType CHECK (type >=0 AND type <= 1),
	CONSTRAINT validAmount CHECK (amount>0) 
) TYPE InnoDB;

CREATE INDEX AccountOpIndexByAccOpId ON AccountOp(accOpId);
CREATE INDEX AccountOpIndexByAccId ON AccountOp(accOpId,accId);
CREATE INDEX AccountOpIndexByOpTypeId ON AccountOp(accOpId,accId,type);
CREATE INDEX AccountOpIndexByDate ON AccountOp(accOpId,accId,date);

-- Category table
CREATE TABLE Category (
	categoryId BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	version BIGINT,
	CONSTRAINT CategoryPK PRIMARY KEY (categoryId)
) TYPE InnoDB;

-- Operations categorized table
CREATE TABLE OpCategory (
	categoryId BIGINT NOT NULL,
	accOpId BIGINT NOT NULL,
	CONSTRAINT OpCategoryPK PRIMARY KEY (categoryId,accOpId),
	CONSTRAINT OpCategoryCatIdFK FOREIGN KEY(categoryId) REFERENCES Category (categoryId)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT OpCategoryAccOpIdFK FOREIGN KEY(accOpId) REFERENCES AccountOp (accOpId)
	    ON DELETE CASCADE ON UPDATE CASCADE
) TYPE InnoDB;

CREATE INDEX OpCategoryByCategoryId ON OpCategory(categoryId,accOpId);
CREATE INDEX OpCategoryByAccOpId ON OpCategory(accOpId,categoryId);