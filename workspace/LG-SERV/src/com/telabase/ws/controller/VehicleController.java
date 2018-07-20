
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
	public class VehicleController {
		
		private final static Logger logger = Logger.getLogger(VehicleController.class.getName());
		
	@RequestMapping(value = "/api/vehicle/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Vehicle> list(HttpServletRequest request) {VehicleDAO serve = new VehicleDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/vehicle/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody Vehicle o, HttpServletRequest request) {

			VehicleDAO serve = new VehicleDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/vehicle/get", method = RequestMethod.GET)
		@ResponseBody
		public Vehicle findById(HttpServletRequest request) {

			VehicleDAO serve = new VehicleDAO();
			Vehicle o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/vehicle/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			VehicleDAO serve = new VehicleDAO();
			try {
				Vehicle o = new Vehicle();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/vehicle/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody Vehicle[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (Vehicle d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/emcenter/vehicle/list", method = RequestMethod.GET)
		@ResponseBody
		public List<Vehicle> listByEmCenter(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			VehicleDAO dao = new VehicleDAO();
			List<Vehicle> rs = dao.findVehicleByEmCenterId(id);

			return rs;
		}

	
	}
	
	
	