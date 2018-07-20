
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


public class GoalDAO {
	
	
	protected static String ENTITY_NAME = "Goal";
	
	protected DatastoreService ds;
	public GoalDAO() {
		ds = DatastoreServiceFactory.getDatastoreService();
	}
	public Goal convert(Entity entity){
		
		if(entity!=null){
			
			Map<String, Object> propMap = entity.getProperties();
			Iterator propSet = propMap.keySet().iterator();
			HashMap<String, Double> measuresMap = new HashMap<String, Double>();
			String propName;
			Double propValue;
			while(propSet.hasNext()){
				propName = (String)propSet.next();
				if(!propName.startsWith("prop_"))
					continue;
				propValue = (Double)propMap.get(propName);
				measuresMap.put(propName.substring(5), propValue);
			}
			Goal obj = new Goal();
			obj.setId(entity.getKey().getId());
			obj.setProps(measuresMap);
			obj.setPlayerId((long) entity.getProperty("playerId"));
			obj.setModifiedDate((Date) entity.getProperty("modifiedDate"));
		obj.setTargetId((long) entity.getProperty("targetId"));
		
			return obj;
		} else
			return null;
	}
	public void insert(Goal v) {
		Entity e = new Entity(ENTITY_NAME);
		e.setProperty("playerId", v.getPlayerId());
		e.setProperty("modifiedDate", v.getModifiedDate());
		e.setProperty("targetId", v.getTargetId());
		
		HashMap<String, Double> map = v.getProps();
		for(String key : map.keySet()) {
			e.setProperty("prop_"+key, map.get(key));
		}
		ds.put(e);
		
	}
	
	public List<Goal> findGoalByPlayerId(long id) {
		Filter filter = new FilterPredicate("playerId", FilterOperator.EQUAL, id);
		Query q = new Query(ENTITY_NAME).setFilter(filter);
		List<Entity> result = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		List<Goal> list = new ArrayList<Goal>();
		for(Entity e: result) {
			list.add(convert(e));
		}
		
		return list;
		
	}
			
	public List<Goal> findGoalByTargetId(long id) {
		Filter filter = new FilterPredicate("targetId", FilterOperator.EQUAL, id);
		Query q = new Query(ENTITY_NAME).setFilter(filter);
		List<Entity> result = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		List<Goal> list = new ArrayList<Goal>();
		for(Entity e: result) {
			list.add(convert(e));
		}
		
		return list;
		
	}
			


}
	