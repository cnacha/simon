
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
	public class CarePermitController {
		
		private final static Logger logger = Logger.getLogger(CarePermitController.class.getName());
		
	@RequestMapping(value = "/api/carepermit/list", method = RequestMethod.GET)
		@ResponseBody
		public List<CarePermit> list(HttpServletRequest request) {CarePermitDAO serve = new CarePermitDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/carepermit/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody CarePermit o, HttpServletRequest request) {

			CarePermitDAO serve = new CarePermitDAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/carepermit/get", method = RequestMethod.GET)
		@ResponseBody
		public CarePermit findById(HttpServletRequest request) {

			CarePermitDAO serve = new CarePermitDAO();
			CarePermit o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/carepermit/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			CarePermitDAO serve = new CarePermitDAO();
			try {
				CarePermit o = new CarePermit();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	@RequestMapping(value = "/admin/carepermit/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody CarePermit[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (CarePermit d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/patient/carepermit/list", method = RequestMethod.GET)
		@ResponseBody
		public List<CarePermit> listByPatient(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			CarePermitDAO dao = new CarePermitDAO();
			List<CarePermit> rs = dao.findCarePermitByPatientId(id);

			return rs;
		}

	@RequestMapping(value = "/api/caregiver/carepermit/list", method = RequestMethod.GET)
		@ResponseBody
		public List<CarePermit> listByCareGiver(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			CarePermitDAO dao = new CarePermitDAO();
			List<CarePermit> rs = dao.findCarePermitByCareGiverId(id);

			return rs;
		}

	
	}
	
	
	