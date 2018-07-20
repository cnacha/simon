
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


public class EmCenterDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(EmCenter.class) 
		          .count();
	}
	
	public List<EmCenter> list(){
		List<EmCenter> results = ObjectifyService.ofy()
		          .load()
		          .type(EmCenter.class) 
		          .list();

		return results;
	}
	
	public void save(EmCenter o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(EmCenter.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public EmCenter findById(long id){
		return ObjectifyService.ofy().load().type(EmCenter.class).id(id).now();
	}

	public void delete(EmCenter o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<EmCenter> searchByName(String keyword) {
			List<EmCenter> results = ObjectifyService.ofy()
					  .load()
					  .type(EmCenter.class)
					  .filter("name >=", keyword).filter("name <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		


}
	