package com.telabase.security.dao;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.telabase.security.entity.User;

public class UserDAO {
	public int count() {

		return ObjectifyService.ofy()
		          .load()
		          .type(User.class) 
		          .count();
	}
	
	public List<User> list() {

		List<User> results = ObjectifyService.ofy()
		          .load()
		          .type(User.class) 
		          .list();

		return results;

	}
	
	public void save(User o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(User.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public User findById(long id){
		return ObjectifyService.ofy().load().type(User.class).id(id).now();
	}


	public void delete(User o) {

		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public User findUserByAuthorizationKey(String key){
		return ObjectifyService.ofy()
        .load()
        .type(User.class)
        .filter("authorizationKey", key)
        .first().now();
	}
	
	public User findUserByDeviceTokenKey(String key){
		return ObjectifyService.ofy()
        .load()
        .type(User.class)
        .filter("deviceTokenKey", key)
        .first().now();
	}
	
	public User findUser(String username, String password){
		return ObjectifyService.ofy()
		        .load()
		        .type(User.class)
		        .filter("username", username)
		        .filter("password", password)
		        .first().now();
	}
	
	public boolean checkDuplicateUsername(String username){
		User user = ObjectifyService.ofy()
        .load()
        .type(User.class)
        .filter("username", username)
        .first().now();
		if(user!=null)
			return true;
		else
			return false;
	}
	
}
