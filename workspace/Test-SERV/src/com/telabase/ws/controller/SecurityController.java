package com.telabase.ws.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.telabase.security.SecurityManager;
import com.telabase.security.dao.UserDAO;
import com.telabase.security.entity.User;
import com.telabase.ws.model.WSResponse;

@Controller
public class SecurityController {

	private SecurityManager securityManager = new SecurityManager();
	private final static Logger logger = Logger.getLogger(SecurityController.class .getName()); 

	@RequestMapping(value = "/api/security/logout", method = RequestMethod.GET)
	public @ResponseBody WSResponse logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(SecurityManager.USER_DATA_SESSION_ATTRIBUTE, null);
		securityManager.destroyAuthority(request);

		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}

	@RequestMapping(value = "/api/security/login", method = RequestMethod.POST)
	@ResponseBody
	public User login(HttpServletRequest request,@RequestBody User loginBean) {
		logger.info("login: " + loginBean.getUsername() + " " + loginBean.getPassword());
		try {

			UserDAO userService = new UserDAO();
			User validUser = userService.findUser(loginBean.getUsername(), loginBean.getPassword());
			logger.info("found user: "+validUser.getUsername());
			logger.info(validUser.getRole()+"<=>"+loginBean.getRole());
			if (validUser != null && validUser.getRole().equals(loginBean.getRole())) {
				logger.info("Login Successful");
				securityManager.initAuthority(request, validUser);
				return validUser;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/api/security/register", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse register(@RequestBody User user, HttpServletRequest request) {
		logger.info("register " + user.getPassword());

		UserDAO userService = new UserDAO();
		try {
			if(user.getUsername() == null || user.getUsername().equals(""))
				return new WSResponse(WSResponse.STATUS_FAIL, "Username must contain proper text");
			// Check Duplicate user
			if (userService.checkDuplicateUsername(user.getUsername())) {
				return new WSResponse(WSResponse.STATUS_FAIL, "Duplicate Username");
			}

			user.setRole(SecurityManager.ROLE_USER);
			user.setStatus(1);
			userService.save(user);
			
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);


		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(WSResponse.STATUS_FAIL, "Error during registration");
		}

	}

	
}
