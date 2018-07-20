package com.telabase.ws.filter;


import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.telabase.security.SecurityManager;

import org.springframework.web.filter.OncePerRequestFilter;

public class ServiceFilter extends OncePerRequestFilter {
	
	private final static Logger logger = Logger.getLogger(ServiceFilter.class .getName()); 

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filter)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type,"+SecurityManager.AUTHORIZATION_KEY_SESSION_ATTRIBUTE);
		response.addHeader("Access-Control-Max-Age", "1800");
	
		filter.doFilter(request, response);
			
	}

}