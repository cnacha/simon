
package com.telabase.ds;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;
import com.telabase.security.entity.User;

public class OfyHelper implements ServletContextListener{
	public void contextInitialized(ServletContextEvent event) {
		ObjectifyService.register(User.class);
		
		ObjectifyService.register(Employee.class);
		 
	}

	  public void contextDestroyed(ServletContextEvent event) {
	    // App Engine does not currently invoke this method.
	  }
}

	