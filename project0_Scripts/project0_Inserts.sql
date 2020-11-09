--Fill the following three tables with the required data for functionality.

INSERT INTO project0.roles VALUES
	(1,'Administrator'),
	(2,'Employee'),
	(3,'Customer');
	
INSERT INTO project0.account_status VALUES
	(1,'Pending'),
	(2,'Open'),
	(3,'Closed');
	
INSERT INTO project0.account_type VALUES
	(1,'Checking'),
	(2,'Savings');

--Make some users.
	
INSERT INTO project0.users(username, pass, first_name, last_name, roleid) 
	VALUES('qroman', 'qpass', 'Quincy', 'Roman', 1);

INSERT INTO project0.users(username, pass, first_name, last_name, roleid) 
	VALUES('jcaesar', 'invicta', 'Julius', 'Caesar', 2);

INSERT INTO project0.users(username, pass, first_name, last_name, roleid) 
	VALUES('gkhan', 'uraura', 'Genghis', 'Khan', 1);

INSERT INTO project0.users(username, pass, first_name, last_name, roleid) 
	VALUES('cshih', 'cheklapkok', 'Ching', 'Shih', 2);

INSERT INTO project0.users(username, pass, first_name, last_name, roleid) 
	VALUES('tieyasu', 'edo', 'Tokugawa', 'Ieyasu', 3);

INSERT INTO project0.users(username, pass, first_name, last_name, roleid) 
	VALUES('nbonaparte', 'petitmignon', 'Napolean', 'Bonaparte', 3);

INSERT INTO project0.users(username, pass, first_name, last_name, roleid) 
	VALUES('jdarc', 'orleans', 'Joan', 'D`Arc', 3);

INSERT INTO project0.users(username, pass, first_name, last_name, roleid) 
	VALUES('mcihuacoatl', 'lallorona', 'Maria', 'Cihuacoatl', 3);

--Fill the accounts table with dummy data.

INSERT INTO project0.accounts(balance,status,userid,typeid)
	VALUES (5000.50,2,18,1);

INSERT INTO project0.accounts(balance,status,userid,typeid)
	VALUES (530,2,17,2);

INSERT INTO project0.accounts(balance,status,userid,typeid)
	VALUES (1600,2,15,1);

INSERT INTO project0.accounts(balance,status,userid,typeid)
	VALUES (315.44,3,12,1);

--Establish the proper link to owns_accounts.

INSERT INTO project0.owns_accounts (userid,accountid)
	values(18,1);

INSERT INTO project0.owns_accounts (userid,accountid)
	values(17,2);

INSERT INTO project0.owns_accounts (userid,accountid)
	values(15,3);

INSERT INTO project0.owns_accounts (userid,accountid)
	values(12,4);

--Check to make sure everything inserted properly.

SELECT * FROM project0.users;

SELECT * FROM project0.accounts;

SELECT * FROM project0.owns_accounts;