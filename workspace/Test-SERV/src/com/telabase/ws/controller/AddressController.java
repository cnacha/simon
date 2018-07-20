
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
	public class AddressController {
		
		private final static Logger logger = Logger.getLogger(AddressController.class.getName());
		
		@RequestMapping(value = "/api/address/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Address> list(HttpServletRequest request) {AddressDAO serve = new AddressDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/address/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Address o, HttpServletRequest request) {

			AddressDAO serve = new AddressDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/address/get", method = RequestMethod.GET)
		@ResponseBody
		public Address findById(HttpServletRequest request) {

			AddressDAO serve = new AddressDAO();
			Address o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/address/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			AddressDAO serve = new AddressDAO();
			try {
				Address o = new Address();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/admin/address/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Address[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Address d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/employee/address/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Address> listByEmployee(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			AddressDAO dao = new AddressDAO();
			List<Address> rs = dao.findAddressByEmployeeId(id);

			return rs;
		}

	
	}
	
	
	