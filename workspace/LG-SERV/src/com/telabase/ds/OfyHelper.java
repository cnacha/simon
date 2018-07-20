
package com.telabase.ds;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;
import com.telabase.security.entity.User;

public class OfyHelper implements ServletContextListener{
	public void contextInitialized(ServletContextEvent event) {
		ObjectifyService.register(User.class);
		
		ObjectifyService.register(Patient.class);
		
		ObjectifyService.register(LocationLog.class);
		
		ObjectifyService.register(EmRequest.class);
		
		ObjectifyService.register(EmRequestStatusLog.class);
		
		ObjectifyService.register(CareGiver.class);
		
		ObjectifyService.register(CarePermit.class);
		
		ObjectifyService.register(EmCenter.class);
		
		ObjectifyService.register(Vehicle.class);
		
		ObjectifyService.register(DistanceAlert.class);
		 
	}

	  public void contextDestroyed(ServletContextEvent event) {
	    // App Engine does not currently invoke this method.
	  }
}

	