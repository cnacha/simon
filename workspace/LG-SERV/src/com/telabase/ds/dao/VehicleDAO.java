
package com.telabase.ds.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

import java.util.*;


public class VehicleDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Vehicle.class) 
		          .count();
	}
	
	public List<Vehicle> list(){
		List<Vehicle> results = ObjectifyService.ofy()
		          .load()
		          .type(Vehicle.class) 
		          .list();

		return results;
	}
	
	public void save(Vehicle o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Vehicle.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Vehicle findById(long id){
		return ObjectifyService.ofy().load().type(Vehicle.class).id(id).now();
	}

	public void delete(Vehicle o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<Vehicle> searchByNumber(String keyword) {
			List<Vehicle> results = ObjectifyService.ofy()
					  .load()
					  .type(Vehicle.class)
					  .filter("number >=", keyword).filter("number <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		public List<Vehicle> findVehicleByEmCenterId(long emCenterId){
		List<Vehicle> results = ObjectifyService.ofy()
		          .load()
		          .type(Vehicle.class)
		          .filter("emCenterId", emCenterId)
		          .list();

		return results;
	}
		


}
	