
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


public class EmRequestStatusLogDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(EmRequestStatusLog.class) 
		          .count();
	}
	
	public List<EmRequestStatusLog> list(){
		List<EmRequestStatusLog> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequestStatusLog.class) 
		          .list();

		return results;
	}
	
	public void save(EmRequestStatusLog o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(EmRequestStatusLog.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public EmRequestStatusLog findById(long id){
		return ObjectifyService.ofy().load().type(EmRequestStatusLog.class).id(id).now();
	}

	public void delete(EmRequestStatusLog o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<EmRequestStatusLog> findEmRequestStatusLogByEmRequestId(long emRequestId){
		List<EmRequestStatusLog> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequestStatusLog.class)
		          .filter("emRequestId", emRequestId)
		          .list();

		return results;
	}
		


}
	