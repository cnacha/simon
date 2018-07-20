package com.telabase.security;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

import com.telabase.util.StringUtil;
import com.telabase.security.dao.UserDAO;
import com.telabase.security.entity.User;

public class SecurityManager {
	private final static Logger logger = Logger.getLogger(SecurityManager.class .getName()); 

	public static final String AUTHORIZATION_KEY_SESSION_ATTRIBUTE = "___authorizationkey";
	public static final String USER_DATA_SESSION_ATTRIBUTE = "____userData";
	
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_SUPERUSER = "superuser";
	public static final String ROLE_USER = "user";
	
	public void initAuthority(HttpServletRequest request, User loggedinUser){
		logger.info("initAuthority()");
	//	if(request.getRequestURL().indexOf(""))
		UserDAO dao = new UserDAO();
		try {
			
			loggedinUser.setAuthorizationKey(StringUtil.randomString(10));
			dao.save(loggedinUser);
			
			// waiting until entity is updated
			dao = new UserDAO();
			User tmp = null;
			while(tmp == null){
				tmp =  dao.findUserByAuthorizationKey(loggedinUser.getAuthorizationKey());
				logger.finest("		waiting for" +tmp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("logging authorization Key "+loggedinUser.getAuthorizationKey()+" for "+loggedinUser.getUsername());
		request.getSession().setAttribute(AUTHORIZATION_KEY_SESSION_ATTRIBUTE, loggedinUser.getAuthorizationKey());
		request.getSession().setAttribute(USER_DATA_SESSION_ATTRIBUTE, loggedinUser);
	}
	
	public void initAuthorityWithoutPersistKey(HttpServletRequest request, User loggedinUser){
		logger.info("initAuthority()");
		logger.info("logging authorization Key "+loggedinUser.getAuthorizationKey()+" for "+loggedinUser.getUsername());
		loggedinUser.setAuthorizationKey(StringUtil.randomString(10));
		request.getSession().setAttribute(AUTHORIZATION_KEY_SESSION_ATTRIBUTE, loggedinUser.getAuthorizationKey());
		request.getSession().setAttribute(USER_DATA_SESSION_ATTRIBUTE, loggedinUser);
	}
	
	public void destroyAuthority(HttpServletRequest request){
		request.getSession().setAttribute(AUTHORIZATION_KEY_SESSION_ATTRIBUTE, null);
		request.getSession().setAttribute(USER_DATA_SESSION_ATTRIBUTE, null);
	}
	
	public boolean validateAuthority(HttpServletRequest request){
		String validKey = (String)request.getSession().getAttribute(AUTHORIZATION_KEY_SESSION_ATTRIBUTE);

		if(validKey!=null )
			return true;
		else{
			// query by authorization key
			UserDAO dao = new UserDAO();
			String authorizationKey = request.getHeader(AUTHORIZATION_KEY_SESSION_ATTRIBUTE);
			if(authorizationKey == null || "".equals(authorizationKey))
				return false;
			User user = dao.findUserByAuthorizationKey(authorizationKey);
			logger.info("validating authorization Key "+authorizationKey+" for "+user);
			if(user!=null)
				return true;
			else
				return false;
		}
	}
	
	public boolean validateAuthorityWithRole(HttpServletRequest request, String requiredRole){
		User user = (User)request.getSession().getAttribute(USER_DATA_SESSION_ATTRIBUTE);
	
		if(user == null){
			String authorizationKey = request.getHeader(AUTHORIZATION_KEY_SESSION_ATTRIBUTE);
			UserDAO dao = new UserDAO();
			User foundUser = dao.findUserByAuthorizationKey(authorizationKey);
			if(foundUser!=null && requiredRole.equalsIgnoreCase(foundUser.getRole()))
				return true;
		}
		return false;
	}
}
