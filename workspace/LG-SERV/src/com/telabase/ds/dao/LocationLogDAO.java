
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


public class LocationLogDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(LocationLog.class) 
		          .count();
	}
	
	public List<LocationLog> list(){
		List<LocationLog> results = ObjectifyService.ofy()
		          .load()
		          .type(LocationLog.class) 
		          .list();

		return results;
	}
	
	public void save(LocationLog o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(LocationLog.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public LocationLog findById(long id){
		return ObjectifyService.ofy().load().type(LocationLog.class).id(id).now();
	}

	public void delete(LocationLog o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<LocationLog> findLocationLogByPatientId(long patientId){
		List<LocationLog> results = ObjectifyService.ofy()
		          .load()
		          .type(LocationLog.class)
		          .filter("patientId", patientId)
		          .list();

		return results;
	}
		


}
	