
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


public class StatusDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Status.class) 
		          .count();
	}
	
	public List<Status> list(){
		List<Status> results = ObjectifyService.ofy()
		          .load()
		          .type(Status.class) 
		          .list();

		return results;
	}
	
	public void save(Status o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Status.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Status findById(long id){
		return ObjectifyService.ofy().load().type(Status.class).id(id).now();
	}

	public void delete(Status o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<Status> findStatusByPlayerId(long playerId){
		List<Status> results = ObjectifyService.ofy()
		          .load()
		          .type(Status.class)
		          .filter("playerId", playerId)
		          .list();

		return results;
	}
		public List<Status> findStatusByTargetId(long targetId){
		List<Status> results = ObjectifyService.ofy()
		          .load()
		          .type(Status.class)
		          .filter("targetId", targetId)
		          .list();

		return results;
	}
		


}
	