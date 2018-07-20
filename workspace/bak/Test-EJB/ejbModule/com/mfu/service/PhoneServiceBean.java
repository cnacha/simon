
	package com.mfu.service;

	import com.mfu.entity.*;
	import java.util.List;
	import javax.ejb.Remote;
	import javax.ejb.Stateless;
	import javax.persistence.EntityManager;
	import javax.persistence.PersistenceContext;

	@Stateless
	@Remote(PhoneService.class)
	public class PhoneServiceBean implements PhoneService{

		@PersistenceContext(unitName = "app-database")
		EntityManager em;

		// Genereated default methods for CRUD
		@Override
		public void create(Phone param){
			em.persist(param);
		}
		
		@Override
		public void update(Phone param){
			em.merge(param);
		}
		
		@Override
		public void delete(long id){
			Phone obj = findPhoneById(id);
			if(obj!=null){
			
				// destroy relationship to Employee
				List<Employee> objList = em.createQuery("SELECT obj.employee FROM Phone obj WHERE obj.id='"+id+"'").getResultList();
				for(Employee rel: objList){
					
					rel.getPhone().remove(obj);
						
					em.merge(rel);
				}
				em.remove(obj);
			}
		}

		@Override
		public Phone findPhoneById(long id){
			Phone obj = em.find(Phone.class, id);
			return obj;
		}
		@Override
		public List<Phone> getAllPhone(){
			return em.createQuery("SELECT ent FROM Phone ent").getResultList();
		}

		
		// Generated code for query by attribute: number
		@Override
		public List<Phone> findPhoneByNumber(String param){
			return em.createQuery("SELECT ent FROM Phone ent WHERE ent.number LIKE :pm").setParameter("pm", "%"+param+"%").getResultList();
		}
	   
	        // Generated code for searching by parent relationship:
		public List<Phone> findPhoneByEmployeeId(long id){
			return em.createQuery("SELECT ent FROM Phone ent WHERE ent.employee.id = :pm").setParameter("pm", id).getResultList();
		}
	   

	}
	