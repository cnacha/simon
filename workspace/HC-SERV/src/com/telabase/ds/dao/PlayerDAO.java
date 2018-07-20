
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


public class PlayerDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Player.class) 
		          .count();
	}
	
	public List<Player> list(){
		List<Player> results = ObjectifyService.ofy()
		          .load()
		          .type(Player.class) 
		          .list();

		return results;
	}
	
	public void save(Player o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Player.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Player findById(long id){
		return ObjectifyService.ofy().load().type(Player.class).id(id).now();
	}

	public void delete(Player o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<Player> searchByName(String keyword) {
			List<Player> results = ObjectifyService.ofy()
					  .load()
					  .type(Player.class)
					  .filter("name >=", keyword).filter("name <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		public List<Player> findPlayerByClusterId(long clusterId){
		List<Player> results = ObjectifyService.ofy()
		          .load()
		          .type(Player.class)
		          .filter("clusterId", clusterId)
		          .list();

		return results;
	}
		


}
	