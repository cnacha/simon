
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
	public class EmCenterController {
		
		private final static Logger logger = Logger.getLogger(EmCenterController.class.getName());
		
	@RequestMapping(value = "/api/emcenter/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmCenter> list(HttpServletRequest request) {EmCenterDAO serve = new EmCenterDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/emcenter/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody EmCenter o, HttpServletRequest request) {

			EmCenterDAO serve = new EmCenterDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/emcenter/get", method = RequestMethod.GET)
		@ResponseBody
		public EmCenter findById(HttpServletRequest request) {

			EmCenterDAO serve = new EmCenterDAO();
			EmCenter o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/emcenter/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			EmCenterDAO serve = new EmCenterDAO();
			try {
				EmCenter o = new EmCenter();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/emcenter/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody EmCenter[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (EmCenter d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		
	}
	
	
	