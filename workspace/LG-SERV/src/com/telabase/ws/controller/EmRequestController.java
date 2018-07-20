
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
	public class EmRequestController {
		
		private final static Logger logger = Logger.getLogger(EmRequestController.class.getName());
		
	@RequestMapping(value = "/api/emrequest/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmRequest> list(HttpServletRequest request) {EmRequestDAO serve = new EmRequestDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/emrequest/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody EmRequest o, HttpServletRequest request) {

			EmRequestDAO serve = new EmRequestDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/emrequest/get", method = RequestMethod.GET)
		@ResponseBody
		public EmRequest findById(HttpServletRequest request) {

			EmRequestDAO serve = new EmRequestDAO();
			EmRequest o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/emrequest/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			EmRequestDAO serve = new EmRequestDAO();
			try {
				EmRequest o = new EmRequest();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/emrequest/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody EmRequest[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (EmRequest d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/patient/emrequest/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmRequest> listByPatient(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			EmRequestDAO dao = new EmRequestDAO();
			List<EmRequest> rs = dao.findEmRequestByPatientId(id);

			return rs;
		}

	@RequestMapping(value = "/api/emcenter/emrequest/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmRequest> listByEmCenter(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			EmRequestDAO dao = new EmRequestDAO();
			List<EmRequest> rs = dao.findEmRequestByEmCenterId(id);

			return rs;
		}

	
	}
	
	
	