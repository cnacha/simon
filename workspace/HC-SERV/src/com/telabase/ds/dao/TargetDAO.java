
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


public class TargetDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Target.class) 
		          .count();
	}
	
	public List<Target> list(){
		List<Target> results = ObjectifyService.ofy()
		          .load()
		          .type(Target.class) 
		          .list();

		return results;
	}
	
	public void save(Target o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Target.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Target findById(long id){
		return ObjectifyService.ofy().load().type(Target.class).id(id).now();
	}

	public void delete(Target o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<Target> searchByName(String keyword) {
			List<Target> results = ObjectifyService.ofy()
					  .load()
					  .type(Target.class)
					  .filter("name >=", keyword).filter("name <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		


}
	