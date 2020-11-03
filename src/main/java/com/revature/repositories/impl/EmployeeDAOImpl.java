package com.revature.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.repositories.EmployeeDAO;

public class EmployeeDAOImpl implements EmployeeDAO{

	private static Logger log = Logger.getLogger(EmployeeDAOImpl.class);
	
	@Override
	public List<Employee> findAll() {
		List<Employee> list = new ArrayList<Employee>();
		
		//establish connection to DB.
		
		//include SQL to talk to the DB.
		
		return list;
	}
	
	@Override
	public Employee findById(int id) {
		Employee e = new Employee();
		return e;
	}

	@Override
	public boolean insert(Employee e) {
		boolean flag = false;
		return flag;
	}

	@Override
	public boolean update(Employee e) {
		boolean flag = false;
		return flag;
	}

}
