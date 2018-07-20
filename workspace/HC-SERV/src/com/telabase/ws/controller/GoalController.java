
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
	public class GoalController {
		
		private final static Logger logger = Logger.getLogger(GoalController.class.getName());
		
	@RequestMapping(value = "/api/goal/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Goal o, HttpServletRequest request) {
			try {
				GoalDAO dao = new GoalDAO();
				dao.insert(o);

			} catch (Exception e) {
				logger.warning(e.getMessage());
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
				
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}

	@RequestMapping(value = "/admin/goal/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Goal[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Goal d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/player/goal/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Goal> listByPlayer(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			GoalDAO dao = new GoalDAO();
			List<Goal> rs = dao.findGoalByPlayerId(id);

			return rs;
		}

	@RequestMapping(value = "/api/target/goal/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Goal> listByTarget(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			GoalDAO dao = new GoalDAO();
			List<Goal> rs = dao.findGoalByTargetId(id);

			return rs;
		}

	
	}
	
	
	