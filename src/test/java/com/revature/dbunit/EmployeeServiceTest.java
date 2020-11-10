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
import com.revature.repositories.EmployeeDAO;
import com.revature.repositories.impl.EmployeeDAOImpl;

public class EmployeeServiceTest extends DBTestCase{

	private EmployeeDAO eService;
	
	public EmployeeServiceTest(String name) {
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
	
	public void testFindCustomers() {
		Connection conn = ConnectionUtil.getConnection();
		eService = new EmployeeDAOImpl();
		boolean actual = eService.findCustomers();
		
		assertEquals(true, actual);
	}

}
