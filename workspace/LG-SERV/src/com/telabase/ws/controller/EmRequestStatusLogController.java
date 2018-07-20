
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
	public class EmRequestStatusLogController {
		
		private final static Logger logger = Logger.getLogger(EmRequestStatusLogController.class.getName());
		
	@RequestMapping(value = "/api/emrequeststatuslog/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmRequestStatusLog> list(HttpServletRequest request) {EmRequestStatusLogDAO serve = new EmRequestStatusLogDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/emrequeststatuslog/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody EmRequestStatusLog o, HttpServletRequest request) {

			EmRequestStatusLogDAO serve = new EmRequestStatusLogDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/emrequeststatuslog/get", method = RequestMethod.GET)
		@ResponseBody
		public EmRequestStatusLog findById(HttpServletRequest request) {

			EmRequestStatusLogDAO serve = new EmRequestStatusLogDAO();
			EmRequestStatusLog o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/emrequeststatuslog/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			EmRequestStatusLogDAO serve = new EmRequestStatusLogDAO();
			try {
				EmRequestStatusLog o = new EmRequestStatusLog();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/emrequeststatuslog/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody EmRequestStatusLog[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (EmRequestStatusLog d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/emrequest/emrequeststatuslog/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmRequestStatusLog> listByEmRequest(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			EmRequestStatusLogDAO dao = new EmRequestStatusLogDAO();
			List<EmRequestStatusLog> rs = dao.findEmRequestStatusLogByEmRequestId(id);

			return rs;
		}

	
	}
	
	
	