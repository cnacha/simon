
	package com.telabase.ws.controller;

	import java.util.ArrayList;
	import java.util.Enumeration;
	import java.util.List;
	import java.util.logging.Logger;

	import javax.servlet.http.HttpServletRequest;

	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.ResponseBody;
	import com.google.gson.JsonElement;
	import com.telabase.ds.dao.*;
	import com.telabase.ds.entity.*;
	import com.telabase.ws.model.WSResponse;

	@Controller
	public class EmployeeController {
		
		private final static Logger logger = Logger.getLogger(EmployeeController.class.getName());
		
	@RequestMapping(value = "/api/employee/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Employee> list(HttpServletRequest request) {EmployeeDAO serve = new EmployeeDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/employee/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Employee o, HttpServletRequest request) {

			EmployeeDAO serve = new EmployeeDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/employee/get", method = RequestMethod.GET)
		@ResponseBody
		public Employee findById(HttpServletRequest request) {

			EmployeeDAO serve = new EmployeeDAO();
			Employee o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/employee/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			EmployeeDAO serve = new EmployeeDAO();
			try {
				Employee o = new Employee();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/employee/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Employee[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Employee d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		
	}
	
	
	