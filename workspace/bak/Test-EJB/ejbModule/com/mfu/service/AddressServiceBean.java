
	package com.mfu.service;

	import com.mfu.entity.*;
	import java.util.List;
	import javax.ejb.Remote;
	import javax.ejb.Stateless;
	import javax.persistence.EntityManager;
	import javax.persistence.PersistenceContext;

	@Stateless
	@Remote(AddressService.class)
	public class AddressServiceBean implements AddressService{

		@PersistenceContext(unitName = "app-database")
		EntityManager em;

		// Genereated default methods for CRUD
		@Override
		public void create(Address param){
			em.persist(param);
		}
		
		@Override
		public void update(Address param){
			em.merge(param);
		}
		
		@Override
		public void delete(long id){
			Address obj = findAddressById(id);
			if(obj!=null){
			
				// destroy relationship to Employee
				List<Employee> objList = em.createQuery("SELECT obj.employee FROM Address obj WHERE obj.id='"+id+"'").getResultList();
				for(Employee rel: objList){
					
					rel.setAddress(null);
						
					em.merge(rel);
				}
				em.remove(obj);
			}
		}

		@Override
		public Address findAddressById(long id){
			Address obj = em.find(Address.class, id);
			return obj;
		}
		@Override
		public List<Address> getAllAddress(){
			return em.createQuery("SELECT ent FROM Address ent").getResultList();
		}

		
	        // Generated code for searching by parent relationship:
		public List<Address> findAddressByEmployeeId(long id){
			return em.createQuery("SELECT ent FROM Address ent WHERE ent.employee.id = :pm").setParameter("pm", id).getResultList();
		}
	   

	}
	