DROP TABLE IF EXISTS Login;
DROP TABLE IF EXISTS Progress;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Customer;

-- Create the Customer table
CREATE TABLE Customer
(
    customerID        INT IDENTITY(1,1) PRIMARY KEY,
    firstName         VARCHAR(50),
    lastName          VARCHAR(50),
    dob               DATE
);

-- Create the Login table
CREATE TABLE Login
(
    loginID  INT IDENTITY(1,1) PRIMARY KEY,
    customerID INT                NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(64)        NOT NULL,
    FOREIGN KEY (customerID) REFERENCES Customer (customerID) ON DELETE CASCADE
);

-- Create the Course table
CREATE TABLE Course
(
	courseID  INT IDENTITY(1,1) PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL,
	description TEXT,
	content TEXT NOT NULL
);

-- Create the Progress table
CREATE TABLE Progress
(
	progressID  INT IDENTITY(1,1) PRIMARY KEY,
	customerID INT NOT NULL,
	courseID INT NOT NULL,
	value TINYINT DEFAULT 0,
	UNIQUE (customerID,courseID) ,
	FOREIGN KEY (customerID) REFERENCES Customer (customerID) ON DELETE CASCADE,
	FOREIGN KEY (courseID) REFERENCES Course (courseID) ON DELETE CASCADE
);
INSERT INTO Course (name,description,content) VALUES ('Mortgages','A description', 'A mortgage is a type of long-term loan you can use to help buy a house, flat or another type of property. In simple terms, its an agreement between a lender and borrower, secured on a property./~The average mortgage repayment term is 25 years.

However, some borrowers may choose a longer term to pay lower monthly costs.

In 2023, for example, the number of first-time buyers taking out a 31-35 year mortgage hit a record high.

When buying a new home, most people don’t pay for it all in one go. Mortgages let you spread the cost – typically, over something like 25 years.

Learn more about how mortgages work before you apply.');
INSERT INTO Course (name,description,content) VALUES ('Pensions','A description', 'the pension content');
INSERT INTO Course (name,description,content) VALUES ('Savings','A description', 'the savings content');
INSERT INTO Course (name,description,content) VALUES ('Investments','A description', 'the investments content');