
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
	public class CoordinateController {
		
		private final static Logger logger = Logger.getLogger(CoordinateController.class.getName());
		
	@RequestMapping(value = "/api/coordinate/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Coordinate o, HttpServletRequest request) {
			try {
				CoordinateDAO dao = new CoordinateDAO();
				dao.insert(o);

			} catch (Exception e) {
				logger.warning(e.getMessage());
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
				
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}

	@RequestMapping(value = "/admin/coordinate/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Coordinate[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Coordinate d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/player/coordinate/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Coordinate> listByPlayer(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			CoordinateDAO dao = new CoordinateDAO();
			List<Coordinate> rs = dao.findCoordinateByPlayerId(id);

			return rs;
		}

	@RequestMapping(value = "/api/target/coordinate/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Coordinate> listByTarget(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			CoordinateDAO dao = new CoordinateDAO();
			List<Coordinate> rs = dao.findCoordinateByTargetId(id);

			return rs;
		}

	
	}
	
	
	