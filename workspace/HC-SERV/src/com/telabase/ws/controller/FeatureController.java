
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
	public class FeatureController {
		
		private final static Logger logger = Logger.getLogger(FeatureController.class.getName());
		
	@RequestMapping(value = "/api/feature/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Feature> list(HttpServletRequest request) {FeatureDAO serve = new FeatureDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/feature/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Feature o, HttpServletRequest request) {

			FeatureDAO serve = new FeatureDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/feature/get", method = RequestMethod.GET)
		@ResponseBody
		public Feature findById(HttpServletRequest request) {

			FeatureDAO serve = new FeatureDAO();
			Feature o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/feature/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			FeatureDAO serve = new FeatureDAO();
			try {
				Feature o = new Feature();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/feature/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Feature[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Feature d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/target/feature/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Feature> listByTarget(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			FeatureDAO dao = new FeatureDAO();
			List<Feature> rs = dao.findFeatureByTargetId(id);

			return rs;
		}

	
	}
	
	
	