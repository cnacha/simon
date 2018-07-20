
	package com.mfu.service;

	import com.mfu.entity.*;
	import java.util.List;

	public interface EmployeeService {

		// Genereated default methods for CRUD
		public void create(Employee param);
	
		public void update(Employee param);
	
		public void delete(long id);

		public List<Employee> getAllEmployee();

		public Employee findEmployeeById(long id); 

		
		// Generated code for searching attribute: firstname
		public List<Employee> findEmployeeByFirstname(String param);
	   
		// Generated code for searching attribute: lastname
		public List<Employee> findEmployeeByLastname(String param);
	   
		// Generated code for searching attribute: salary
		public List<Employee> findEmployeeBySalary(int param);
	   
		// Generated code for searching attribute: position
		public List<Employee> findEmployeeByPosition(String param);
	   
		// Generated code for query: findByMoreThanSalary
		public List<Employee> findByMoreThanSalary(int param1 );


		// Generated code for query: findByFirstnameAndLastname
		public List<Employee> findByFirstnameAndLastname(String param1,String param2 );


	}
	