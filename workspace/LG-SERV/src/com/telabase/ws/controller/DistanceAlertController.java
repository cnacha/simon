
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
	public class DistanceAlertController {
		
		private final static Logger logger = Logger.getLogger(DistanceAlertController.class.getName());
		
	@RequestMapping(value = "/api/distancealert/list", method = RequestMethod.GET)
		@ResponseBody
		public List<DistanceAlert> list(HttpServletRequest request) {DistanceAlertDAO serve = new DistanceAlertDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/distancealert/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody DistanceAlert o, HttpServletRequest request) {

			DistanceAlertDAO serve = new DistanceAlertDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/distancealert/get", method = RequestMethod.GET)
		@ResponseBody
		public DistanceAlert findById(HttpServletRequest request) {

			DistanceAlertDAO serve = new DistanceAlertDAO();
			DistanceAlert o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/distancealert/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			DistanceAlertDAO serve = new DistanceAlertDAO();
			try {
				DistanceAlert o = new DistanceAlert();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/distancealert/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody DistanceAlert[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (DistanceAlert d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/patient/distancealert/list", method = RequestMethod.GET)
		@ResponseBody
		public List<DistanceAlert> listByPatient(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			DistanceAlertDAO dao = new DistanceAlertDAO();
			List<DistanceAlert> rs = dao.findDistanceAlertByPatientId(id);

			return rs;
		}

	
	}
	
	
	