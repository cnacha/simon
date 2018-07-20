
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
	public class CareGiverController {
		
		private final static Logger logger = Logger.getLogger(CareGiverController.class.getName());
		
	@RequestMapping(value = "/api/caregiver/list", method = RequestMethod.GET)
		@ResponseBody
		public List<CareGiver> list(HttpServletRequest request) {CareGiverDAO serve = new CareGiverDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/caregiver/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody CareGiver o, HttpServletRequest request) {

			CareGiverDAO serve = new CareGiverDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/caregiver/get", method = RequestMethod.GET)
		@ResponseBody
		public CareGiver findById(HttpServletRequest request) {

			CareGiverDAO serve = new CareGiverDAO();
			CareGiver o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/caregiver/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			CareGiverDAO serve = new CareGiverDAO();
			try {
				CareGiver o = new CareGiver();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/caregiver/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody CareGiver[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (CareGiver d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		
	}
	
	
	