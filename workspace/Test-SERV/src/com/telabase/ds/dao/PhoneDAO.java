
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class PhoneDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Phone.class) 
		          .count();
	}
	
	public List<Phone> list(){
		List<Phone> results = ObjectifyService.ofy()
		          .load()
		          .type(Phone.class) 
		          .list();

		return results;
	}
	
	public void save(Phone o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Phone.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Phone findById(long id){
		return ObjectifyService.ofy().load().type(Phone.class).id(id).now();
	}

	public void delete(Phone o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public List<Phone> searchByNumber(String keyword) {
			List<Phone> results = ObjectifyService.ofy()
					  .load()
					  .type(Phone.class)
					  .filter("number >=", keyword).filter("number <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		public List<Phone> findPhoneByEmployeeId(long employeeId){
		List<Phone> results = ObjectifyService.ofy()
		          .load()
		          .type(Phone.class)
		          .filter("employeeId", employeeId)
		          .list();

		return results;
	}
		
}
	