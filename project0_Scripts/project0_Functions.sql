/*
 * Utilizing SQL stored procedures and functions, it is possible to add
 * another layer of abstraction and also remove some of the computing 
 * power needed from my Java program.
 */

--Select all customers.
CREATE OR REPLACE FUNCTION project0.get_all_customers()
RETURNS SETOF project0.users AS 
$$
	SELECT * 
	FROM project0.users u 
	WHERE u.roleid = 3;
$$ LANGUAGE SQL;

--Select all employees.
CREATE OR REPLACE FUNCTION project0.get_all_employees()
RETURNS SETOF project0.users AS 
$$
	SELECT * 
	FROM project0.users u 
	WHERE u.roleid = 2;
$$ LANGUAGE SQL;

--Select all admins.
CREATE OR REPLACE FUNCTION project0.get_all_admins()
RETURNS SETOF project0.users AS 
$$
	SELECT * 
	FROM project0.users u 
	WHERE u.roleid = 1;
$$ LANGUAGE SQL;

--Select all accounts.
CREATE OR REPLACE FUNCTION project0.get_all_accounts()
RETURNS SETOF project0.accounts AS 
$$
	SELECT * 
	FROM project0.accounts a;
$$ LANGUAGE SQL;

--Get the accounts owned by an individual.
CREATE OR REPLACE FUNCTION project0.get_owned_accounts(_userid integer)
RETURNS SETOF project0.accounts
AS $$
	SELECT a.accountid, a.balance, a.status, a.typeid	--possible error with the NUMERIC balance.
	FROM project0.accounts a, project0.owns_accounts oa
	WHERE oa.userid =_userid AND a.accountid = oa.accountid;
$$ LANGUAGE SQL;

--Can include select accounts by status or type as well, but not required.

--When working with INSERT/UPDATE/DELETE use PROCEDURE instead of FUNCTION, similarly utilize CALL instead of SELECT.

--Procedure to insert a new user.
CREATE PROCEDURE project0.insert_user(_username varchar(20),_pass varchar(20), _fname varchar(20), _lname varchar(20), _rid integer)
LANGUAGE SQL
AS $BODY$
	INSERT INTO project0.users(username,pass,first_name,last_name,roleid)
	VALUES(_username,_pass,_fname,_lname,_rid);
$BODY$

--Insert a new account.
CREATE PROCEDURE project0.insert_account(_userid integer,_status integer,_typeid integer,_balance NUMERIC(20,2))
LANGUAGE SQL
AS $BODY$
	INSERT INTO project0.accounts(status,typeid,balance)
	VALUES(_status,_typeid,_balance);

	--the value of owns_accounts needs to be inserted as well.
	INSERT INTO project0.owns_accounts(userid,accountid)
	VALUES(_userid,(SELECT currval('project0.accounts_accountid_seq')));
$BODY$

--Insert a new joint account, overloaded procedure.
CREATE PROCEDURE project0.insert_account(_user1 integer,_user2 integer,_status integer,_typeid integer,_balance NUMERIC(20,2))
LANGUAGE SQL
AS $BODY$
	INSERT INTO project0.accounts(status,typeid,balance)
	VALUES(_status,_typeid,_balance);

	--two users means two entries into owns_accounts for the single accountId.
	INSERT INTO project0.owns_accounts(userid,accountid)
	VALUES(_user1,(SELECT currval('project0.accounts_accountid_seq')));

	INSERT INTO project0.owns_accounts(userid,accountid)
	VALUES(_user2,(SELECT currval('project0.accounts_accountid_seq')));
$BODY$

--Withdraw from an account. Start with the helper function below.
CREATE OR REPLACE FUNCTION project0.get_balance(_accountid integer)
RETURNS numeric(20,2)
AS $$
	BEGIN
		RETURN (SELECT balance FROM project0.accounts WHERE accountid = _accountid);
	END
$$ LANGUAGE plpgsql;

CREATE PROCEDURE project0.withdraw(_accountid integer, amount NUMERIC(20,2))
LANGUAGE plpgsql
AS $BODY$
	BEGIN
		IF project0.get_balance(_accountid) >= amount THEN	--check to make sure user isn't withdrawing more than they own.
			UPDATE project0.accounts
			SET balance = balance - amount
			WHERE accountid = _accountid;	
		END IF;
	END;
$BODY$ 

--Deposit into an account.
CREATE PROCEDURE project0.deposit(_accountid integer, amount NUMERIC(20,2))
LANGUAGE SQL
AS $BODY$
	UPDATE project0.accounts
	SET balance = balance + amount
	WHERE accountid = _accountid;	
$BODY$

--Transfer from one account to a second account.
CREATE PROCEDURE project0.transfer(_account1 integer, _account2 integer, amount NUMERIC(20,2))
LANGUAGE plpgsql
AS $BODY$
	BEGIN
		IF project0.get_balance(_account1) >= amount THEN	--check to make sure user isn't transfering more than they own.
			UPDATE project0.accounts
			SET balance = balance - amount
			WHERE accountid = _account1;
		
			UPDATE project0.accounts 
			SET balance = balance + amount
			WHERE accountid = _account2;
		END IF;
	END;
$BODY$ 

SELECT * FROM project0.account_status as2;

--Approve a pending account.
CREATE PROCEDURE project0.approve_account(_accountid integer)
LANGUAGE plpgsql
AS $$
	BEGIN
		UPDATE project0.accounts a
		SET a.status = 2
		WHERE a.accountid = _accountid;
	END
$$

--Deny a pending account. I decided to remove it from the database for simplicity's sake.
CREATE PROCEDURE project0.deny_account(_accountid integer)
LANGUAGE plpgsql
AS $$
	BEGIN
		DELETE FROM project0.accounts a
		WHERE a.accountid = _accountid;
	END
$$


/*
 * The main thing I would do differently is check to ensure the account is open before allowing any operations to be
 * done on it. This was an afterthought and I didn't want to ruin what I already had.
 * (Maybe add a "is_open function and then check that in an if before each transaction)
 */
