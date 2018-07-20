
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
	public class TargetController {
		
		private final static Logger logger = Logger.getLogger(TargetController.class.getName());
		
	@RequestMapping(value = "/api/target/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Target> list(HttpServletRequest request) {TargetDAO serve = new TargetDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/target/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Target o, HttpServletRequest request) {

			TargetDAO serve = new TargetDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/target/get", method = RequestMethod.GET)
		@ResponseBody
		public Target findById(HttpServletRequest request) {

			TargetDAO serve = new TargetDAO();
			Target o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/target/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			TargetDAO serve = new TargetDAO();
			try {
				Target o = new Target();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/target/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Target[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Target d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		
	}
	
	
	