package com.revature.dbunit;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import com.revature.connection.ConnectionUtil;
import com.revature.repositories.AdminDAO;
import com.revature.repositories.impl.AdminDAOImpl;

public class AdminServiceTest extends DBTestCase{
	
	private AdminDAO aService;
	
	public AdminServiceTest(String name) {
		super(name);
		Properties prop = new Properties();
		
		try {
			prop.load(new FileReader("C:\\Users\\qroma\\Documents\\Revature\\Projects\\project-0-Metsuryuu\\src\\main\\resources\\application.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "java.sql.DriverManager");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, prop.getProperty("url"));
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, prop.getProperty("username"));
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, prop.getProperty("password"));
		
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		String file = classLoader.getResource("actualDataset.xml").getFile();
		return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
	}
	
	public void testFindAll() {
		Connection conn = ConnectionUtil.getConnection();
		aService = new AdminDAOImpl();
		boolean actual = aService.findAll(1);
		actual = aService.findAll(2);
		actual = aService.findAll(3);
		
		//if any of the above return false, at least one didn't work.
		//The only difference is the SQL so the error would be noticeable.
		
		assertEquals(true, actual);
	}
	
	public void testFindAccounts() {
		Connection conn = ConnectionUtil.getConnection();
		aService = new AdminDAOImpl();
		boolean actual = aService.findAllAccounts();
		
		assertEquals(true, actual);
	}
	
	public void testPendingChoice() {
		Connection conn = ConnectionUtil.getConnection();
		aService = new AdminDAOImpl();
		boolean actual = aService.pendingChoice();
		
		assertEquals(true, actual);
	}
}
