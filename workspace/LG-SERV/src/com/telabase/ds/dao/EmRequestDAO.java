
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


public class EmRequestDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class) 
		          .count();
	}
	
	public List<EmRequest> list(){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class) 
		          .list();

		return results;
	}
	
	public void save(EmRequest o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(EmRequest.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public EmRequest findById(long id){
		return ObjectifyService.ofy().load().type(EmRequest.class).id(id).now();
	}

	public void delete(EmRequest o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<EmRequest> findEmRequestByPatientId(long patientId){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("patientId", patientId)
		          .list();

		return results;
	}
		public List<EmRequest> findEmRequestByEmCenterId(long emCenterId){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("emCenterId", emCenterId)
		          .list();

		return results;
	}
		


}
	