
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
	public class LocationLogController {
		
		private final static Logger logger = Logger.getLogger(LocationLogController.class.getName());
		
	@RequestMapping(value = "/api/locationlog/list", method = RequestMethod.GET)
		@ResponseBody
		public List<LocationLog> list(HttpServletRequest request) {LocationLogDAO serve = new LocationLogDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/locationlog/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody LocationLog o, HttpServletRequest request) {

			LocationLogDAO serve = new LocationLogDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/locationlog/get", method = RequestMethod.GET)
		@ResponseBody
		public LocationLog findById(HttpServletRequest request) {

			LocationLogDAO serve = new LocationLogDAO();
			LocationLog o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/locationlog/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			LocationLogDAO serve = new LocationLogDAO();
			try {
				LocationLog o = new LocationLog();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/locationlog/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody LocationLog[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (LocationLog d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/patient/locationlog/list", method = RequestMethod.GET)
		@ResponseBody
		public List<LocationLog> listByPatient(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			LocationLogDAO dao = new LocationLogDAO();
			List<LocationLog> rs = dao.findLocationLogByPatientId(id);

			return rs;
		}

	
	}
	
	
	