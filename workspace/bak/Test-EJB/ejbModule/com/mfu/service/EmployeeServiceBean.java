
	package com.mfu.service;

	import com.mfu.entity.*;
	import java.util.List;
	import javax.ejb.Remote;
	import javax.ejb.Stateless;
	import javax.persistence.EntityManager;
	import javax.persistence.PersistenceContext;

	@Stateless
	@Remote(EmployeeService.class)
	public class EmployeeServiceBean implements EmployeeService{

		@PersistenceContext(unitName = "app-database")
		EntityManager em;

		// Genereated default methods for CRUD
		@Override
		public void create(Employee param){
			em.persist(param);
		}
		
		@Override
		public void update(Employee param){
			em.merge(param);
		}
		
		@Override
		public void delete(long id){
			Employee obj = findEmployeeById(id);
			if(obj!=null){
			
				em.remove(obj);
			}
		}

		@Override
		public Employee findEmployeeById(long id){
			Employee obj = em.find(Employee.class, id);
			return obj;
		}
		@Override
		public List<Employee> getAllEmployee(){
			return em.createQuery("SELECT ent FROM Employee ent").getResultList();
		}

		
		// Generated code for query by attribute: firstname
		@Override
		public List<Employee> findEmployeeByFirstname(String param){
			return em.createQuery("SELECT ent FROM Employee ent WHERE ent.firstname LIKE :pm").setParameter("pm", "%"+param+"%").getResultList();
		}
	   
		// Generated code for query by attribute: lastname
		@Override
		public List<Employee> findEmployeeByLastname(String param){
			return em.createQuery("SELECT ent FROM Employee ent WHERE ent.lastname LIKE :pm").setParameter("pm", "%"+param+"%").getResultList();
		}
	   
		// Generated code for query by attribute: salary
		@Override
		public List<Employee> findEmployeeBySalary(int param){
			return em.createQuery("SELECT ent FROM Employee ent WHERE ent.salary LIKE :pm").setParameter("pm", "%"+param+"%").getResultList();
		}
	   
		// Generated code for query by attribute: position
		@Override
		public List<Employee> findEmployeeByPosition(String param){
			return em.createQuery("SELECT ent FROM Employee ent WHERE ent.position LIKE :pm").setParameter("pm", "%"+param+"%").getResultList();
		}
	   
		// Generated code for query: findByMoreThanSalary
		@Override
		public List<Employee> findByMoreThanSalary(int param1 ){
			return em.createQuery("SELECT ent FROM Employee ent WHERE ent.salary >= :param1")
			.setParameter("param1", param1)
			.getResultList();
		
		}


		// Generated code for query: findByFirstnameAndLastname
		@Override
		public List<Employee> findByFirstnameAndLastname(String param1,String param2 ){
			return em.createQuery("SELECT ent FROM Employee ent WHERE ent.firstname LIKE :param1 AND ent.lastname LIKE :param2")
			.setParameter("param1", param1+"%")
			.setParameter("param2", param2+"%")
			.getResultList();
		
		}



	}
	