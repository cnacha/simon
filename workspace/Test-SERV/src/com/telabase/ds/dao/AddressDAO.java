
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class AddressDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Address.class) 
		          .count();
	}
	
	public List<Address> list(){
		List<Address> results = ObjectifyService.ofy()
		          .load()
		          .type(Address.class) 
		          .list();

		return results;
	}
	
	public void save(Address o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Address.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Address findById(long id){
		return ObjectifyService.ofy().load().type(Address.class).id(id).now();
	}

	public void delete(Address o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public List<Address> findAddressByEmployeeId(long employeeId){
		List<Address> results = ObjectifyService.ofy()
		          .load()
		          .type(Address.class)
		          .filter("employeeId", employeeId)
		          .list();

		return results;
	}
		
}
	