
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
	public class StatusController {
		
		private final static Logger logger = Logger.getLogger(StatusController.class.getName());
		
	@RequestMapping(value = "/api/status/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Status> list(HttpServletRequest request) {StatusDAO serve = new StatusDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/status/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Status o, HttpServletRequest request) {

			StatusDAO serve = new StatusDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/status/get", method = RequestMethod.GET)
		@ResponseBody
		public Status findById(HttpServletRequest request) {

			StatusDAO serve = new StatusDAO();
			Status o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/status/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			StatusDAO serve = new StatusDAO();
			try {
				Status o = new Status();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/status/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Status[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Status d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/player/status/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Status> listByPlayer(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			StatusDAO dao = new StatusDAO();
			List<Status> rs = dao.findStatusByPlayerId(id);

			return rs;
		}

	@RequestMapping(value = "/api/target/status/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Status> listByTarget(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			StatusDAO dao = new StatusDAO();
			List<Status> rs = dao.findStatusByTargetId(id);

			return rs;
		}

	
	}
	
	
	