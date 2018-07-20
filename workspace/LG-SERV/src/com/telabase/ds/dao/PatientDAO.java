
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


public class PatientDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Patient.class) 
		          .count();
	}
	
	public List<Patient> list(){
		List<Patient> results = ObjectifyService.ofy()
		          .load()
		          .type(Patient.class) 
		          .list();

		return results;
	}
	
	public void save(Patient o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Patient.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Patient findById(long id){
		return ObjectifyService.ofy().load().type(Patient.class).id(id).now();
	}

	public void delete(Patient o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<Patient> searchByFirstname(String keyword) {
			List<Patient> results = ObjectifyService.ofy()
					  .load()
					  .type(Patient.class)
					  .filter("firstname >=", keyword).filter("firstname <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		


}
	