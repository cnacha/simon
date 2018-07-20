
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
	public class PatientController {
		
		private final static Logger logger = Logger.getLogger(PatientController.class.getName());
		
	@RequestMapping(value = "/api/patient/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Patient> list(HttpServletRequest request) {PatientDAO serve = new PatientDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/patient/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Patient o, HttpServletRequest request) {

			PatientDAO serve = new PatientDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/patient/get", method = RequestMethod.GET)
		@ResponseBody
		public Patient findById(HttpServletRequest request) {

			PatientDAO serve = new PatientDAO();
			Patient o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/patient/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			PatientDAO serve = new PatientDAO();
			try {
				Patient o = new Patient();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/patient/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Patient[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Patient d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		
	}
	
	
	