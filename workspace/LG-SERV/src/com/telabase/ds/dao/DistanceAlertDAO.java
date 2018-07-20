
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


public class DistanceAlertDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(DistanceAlert.class) 
		          .count();
	}
	
	public List<DistanceAlert> list(){
		List<DistanceAlert> results = ObjectifyService.ofy()
		          .load()
		          .type(DistanceAlert.class) 
		          .list();

		return results;
	}
	
	public void save(DistanceAlert o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(DistanceAlert.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public DistanceAlert findById(long id){
		return ObjectifyService.ofy().load().type(DistanceAlert.class).id(id).now();
	}

	public void delete(DistanceAlert o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<DistanceAlert> findDistanceAlertByPatientId(long patientId){
		List<DistanceAlert> results = ObjectifyService.ofy()
		          .load()
		          .type(DistanceAlert.class)
		          .filter("patientId", patientId)
		          .list();

		return results;
	}
		


}
	