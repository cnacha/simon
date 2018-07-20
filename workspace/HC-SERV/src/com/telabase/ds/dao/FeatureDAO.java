
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


public class FeatureDAO {
	
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Feature.class) 
		          .count();
	}
	
	public List<Feature> list(){
		List<Feature> results = ObjectifyService.ofy()
		          .load()
		          .type(Feature.class) 
		          .list();

		return results;
	}
	
	public void save(Feature o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Feature.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Feature findById(long id){
		return ObjectifyService.ofy().load().type(Feature.class).id(id).now();
	}

	public void delete(Feature o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	public List<Feature> searchByName(String keyword) {
			List<Feature> results = ObjectifyService.ofy()
					  .load()
					  .type(Feature.class)
					  .filter("name >=", keyword).filter("name <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		public List<Feature> findFeatureByTargetId(long targetId){
		List<Feature> results = ObjectifyService.ofy()
		          .load()
		          .type(Feature.class)
		          .filter("targetId", targetId)
		          .list();

		return results;
	}
		


}
	