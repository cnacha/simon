
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
	public class PlayerController {
		
		private final static Logger logger = Logger.getLogger(PlayerController.class.getName());
		
	@RequestMapping(value = "/api/player/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Player> list(HttpServletRequest request) {PlayerDAO serve = new PlayerDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/player/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Player o, HttpServletRequest request) {

			PlayerDAO serve = new PlayerDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/player/get", method = RequestMethod.GET)
		@ResponseBody
		public Player findById(HttpServletRequest request) {

			PlayerDAO serve = new PlayerDAO();
			Player o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/player/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			PlayerDAO serve = new PlayerDAO();
			try {
				Player o = new Player();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/player/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Player[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Player d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/cluster/player/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Player> listByCluster(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			PlayerDAO dao = new PlayerDAO();
			List<Player> rs = dao.findPlayerByClusterId(id);

			return rs;
		}

	
	}
	
	
	