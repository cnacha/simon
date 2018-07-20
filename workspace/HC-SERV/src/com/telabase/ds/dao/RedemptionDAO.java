
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


public class RedemptionDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Redemption.class) 
		          .count();
	}
	
	public List<Redemption> list(){
		List<Redemption> results = ObjectifyService.ofy()
		          .load()
		          .type(Redemption.class) 
		          .list();

		return results;
	}
	
	public void save(Redemption o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Redemption.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Redemption findById(long id){
		return ObjectifyService.ofy().load().type(Redemption.class).id(id).now();
	}

	public void delete(Redemption o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<Redemption> findRedemptionByPlayerId(long playerId){
		List<Redemption> results = ObjectifyService.ofy()
		          .load()
		          .type(Redemption.class)
		          .filter("playerId", playerId)
		          .list();

		return results;
	}
		


}
	