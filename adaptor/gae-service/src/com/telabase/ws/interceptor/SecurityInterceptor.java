package com.telabase.ws.interceptor;


import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.telabase.security.SecurityManager;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
	private final static Logger logger = Logger.getLogger(SecurityInterceptor.class.getName());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("SecurityInterceptor.preHandle()");
		boolean isWebServiceCall = request.getRequestURL().indexOf("/api/") != -1;
		boolean isAdminCall = (request.getRequestURL().indexOf("/admin/") != -1) && (request.getRequestURL().indexOf("/ah/admin") == -1);
		boolean isAllowAnyAccess = request.getRequestURI().indexOf("/security/") != -1
				|| request.getRequestURI().indexOf("/preset/") != -1;

		logger.info("doFilterInternal is called " + isWebServiceCall + " " + isAdminCall + " " + isAllowAnyAccess);
		System.out.println(
				"	..." + request.getSession().getAttribute(SecurityManager.AUTHORIZATION_KEY_SESSION_ATTRIBUTE));
		SecurityManager secManager = new SecurityManager();

		if (isWebServiceCall 
				&& !isAdminCall 
				&& !isAllowAnyAccess
				&& !secManager.validateAuthority(request)){
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden to access");
			return false;
		}else if (isAdminCall 
				&& !isAllowAnyAccess
				&& !secManager.validateAuthorityWithRole(request, SecurityManager.ROLE_ADMIN)){
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden to access");
			return false;
		}

		return true;
	}
}