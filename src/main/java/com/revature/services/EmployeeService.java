package com.revature.services;

import java.util.List;

import com.revature.model.Employee;
import com.revature.repositories.EmployeeDAO;
import com.revature.repositories.impl.EmployeeDAOImpl;

public class EmployeeService {
	
	EmployeeDAO repository = null;
	
	public EmployeeService() {
		repository = new EmployeeDAOImpl();
	}
	
	public List<Employee> findAll(){
		return repository.findAll();
	}
	
	public boolean insert(Employee e) {
		return repository.insert(e);
	}
	
	public boolean login(String username, String password) {
		boolean flag = false;
		//verify those with the database, then add to a new Employee object.
		return flag;
	}

}
