
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
	public class RedemptionController {
		
		private final static Logger logger = Logger.getLogger(RedemptionController.class.getName());
		
	@RequestMapping(value = "/api/redemption/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Redemption> list(HttpServletRequest request) {RedemptionDAO serve = new RedemptionDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/redemption/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Redemption o, HttpServletRequest request) {

			RedemptionDAO serve = new RedemptionDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/redemption/get", method = RequestMethod.GET)
		@ResponseBody
		public Redemption findById(HttpServletRequest request) {

			RedemptionDAO serve = new RedemptionDAO();
			Redemption o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/redemption/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			RedemptionDAO serve = new RedemptionDAO();
			try {
				Redemption o = new Redemption();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/redemption/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Redemption[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Redemption d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/player/redemption/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Redemption> listByPlayer(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			RedemptionDAO dao = new RedemptionDAO();
			List<Redemption> rs = dao.findRedemptionByPlayerId(id);

			return rs;
		}

	
	}
	
	
	