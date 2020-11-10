/*
CREATE TABLE project0.employee(

	employeeId SERIAL primary key,	
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	username VARCHAR(20) NOT NULL,
	employee_pass varchar(20) NOT NULL,
	salary numeric(10,2) DEFAULT 0,
	supervisor INTEGER REFERENCES project0.employee(employeeId)

);

CREATE TABLE project0.administrator(

	adminId serial PRIMARY KEY,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	username varchar(20) NOT NULL UNIQUE,
	admin_pass varchar(20) NOT NULL,
	salary numeric(10,2) DEFAULT 0,
	supervisor integer REFERENCES project0.administrator(adminId)

); Original idea, but too much repetitive data.*/

CREATE TABLE project0.users(

	userId serial PRIMARY KEY, --make a trigger to set this.
	username varchar(20) NOT NULL UNIQUE,
	pass varchar(20) NOT NULL,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	roleId integer REFERENCES project0.roles(roleId)

);


CREATE TABLE project0.roles(

	roleId serial PRIMARY KEY,
	role_name varchar(15) NOT NULL UNIQUE

);

CREATE TABLE project0.accounts(

	accountId serial PRIMARY KEY, --trigger to set this
	userId integer REFERENCES project0.owns_accounts(userId),
	status integer REFERENCES project0.account_status(statusId),
	typeId integer REFERENCES project0.account_type(typeId),
	balance numeric(20,2) DEFAULT 0
	
);

CREATE TABLE project0.account_status(

	statusId serial PRIMARY KEY,
	status_name varchar(8)

);

CREATE TABLE project0.account_type(

	typeId serial PRIMARY KEY,
	type_name varchar(8)

);

CREATE TABLE project0.owns_accounts(

	ownId serial PRIMARY KEY,
	userId integer REFERENCES project0.users(userId),
	accountId integer REFERENCES project0.accounts(accountId)

);
