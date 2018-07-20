
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


public class CareGiverDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(CareGiver.class) 
		          .count();
	}
	
	public List<CareGiver> list(){
		List<CareGiver> results = ObjectifyService.ofy()
		          .load()
		          .type(CareGiver.class) 
		          .list();

		return results;
	}
	
	public void save(CareGiver o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(CareGiver.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public CareGiver findById(long id){
		return ObjectifyService.ofy().load().type(CareGiver.class).id(id).now();
	}

	public void delete(CareGiver o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<CareGiver> searchByFirstname(String keyword) {
			List<CareGiver> results = ObjectifyService.ofy()
					  .load()
					  .type(CareGiver.class)
					  .filter("firstname >=", keyword).filter("firstname <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		


}
	