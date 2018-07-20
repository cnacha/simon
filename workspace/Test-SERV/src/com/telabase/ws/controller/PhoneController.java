
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
	public class PhoneController {
		
		private final static Logger logger = Logger.getLogger(PhoneController.class.getName());
		
		@RequestMapping(value = "/api/phone/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Phone> list(HttpServletRequest request) {PhoneDAO serve = new PhoneDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/phone/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Phone o, HttpServletRequest request) {

			PhoneDAO serve = new PhoneDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/phone/get", method = RequestMethod.GET)
		@ResponseBody
		public Phone findById(HttpServletRequest request) {

			PhoneDAO serve = new PhoneDAO();
			Phone o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/phone/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			PhoneDAO serve = new PhoneDAO();
			try {
				Phone o = new Phone();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/admin/phone/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Phone[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Phone d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/employee/phone/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Phone> listByEmployee(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			PhoneDAO dao = new PhoneDAO();
			List<Phone> rs = dao.findPhoneByEmployeeId(id);

			return rs;
		}

	
	}
	
	
	