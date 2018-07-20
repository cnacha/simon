
	package com.mfu.web.controller;

	import java.util.List;
	import java.util.Date;
	import java.io.*;
	import javax.servlet.http.HttpServletRequest;
	import org.springframework.stereotype.Controller;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.ServletRequestDataBinder;
	import org.springframework.web.bind.annotation.InitBinder;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.SessionAttributes;
	import org.springframework.web.multipart.commons.CommonsMultipartFile;
	import org.springframework.web.servlet.ModelAndView;
	import com.mfu.web.util.*;
	import com.mfu.dao.*;
	import com.mfu.entity.*;

	@Controller
	public class AddressController {
		
		AddressDAO addressDAO = new AddressDAO();
		
		public AddressController() {
			try {
				addressDAO.init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Generated method for init date property editor 
		@InitBinder
		protected void initBinder(HttpServletRequest req,ServletRequestDataBinder binder) throws Exception {
			binder.registerCustomEditor(Date.class, new DatePropertyEditor());
		}

		//Generated method for listing Address
		@RequestMapping("/listAddress")
		public ModelAndView listAddress(){
		
			ModelAndView mv = new ModelAndView("listAddress.jsp");
			
			List<Address> objList;
			try {
				objList = addressDAO.getAllObject();
				mv.addObject("objList", objList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}

		@RequestMapping("/newAddress")
		public ModelAndView newAddress(HttpServletRequest request){
			ModelAndView mv = new ModelAndView("formAddress.jsp");
			Address obj = new Address();
			
			if(request.getSession().getAttribute("parentId")!=null)
				 obj.setEmployeeEntId((String)request.getSession().getAttribute("parentId"));
			     
			mv.addObject("obj", obj);
		
			return mv;
		}

		@RequestMapping("/saveAddress")
		public String saveAddress(
				@ModelAttribute("address") Address obj, 
				BindingResult result, HttpServletRequest request){
			
			try {
				// obj is not existed, create it
				if(obj.getEntId() == null || "".equals(obj.getEntId())){
					obj.setEntId(null);
					addressDAO.create(obj);
					
				// object is existed
				} else {
					addressDAO.update(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(request.getSession().getAttribute("redirectSaveAddressURL")!=null)
				return "redirect:"+request.getSession().getAttribute("redirectSaveAddressURL");
			else
			
				return "redirect:listAddress.do";
		}

		@RequestMapping("/deleteAddress")
		public String deleteAddress(HttpServletRequest request){
			addressDAO.delete((String)request.getSession().getAttribute("parentId"), request.getParameter("id"));
				
			if(request.getSession().getAttribute("redirectSaveAddressURL")!=null)
				return "redirect:"+request.getSession().getAttribute("redirectSaveAddressURL");
			else
			
				return "redirect:listAddress.do";
		}

		@RequestMapping("/editAddress")
		public ModelAndView editAddress(HttpServletRequest request){
			String paramId = request.getParameter("id");
			
			Address foundObj;
			ModelAndView mv = new ModelAndView("formAddress.jsp");
			try {
			
				foundObj = addressDAO.getObjectById((String)request.getSession().getAttribute("parentId"), paramId);
			
				mv.addObject("obj", foundObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mv;
		}

		
		@RequestMapping("/editAddressByEmployee")
		public String editAddressByEmployee(HttpServletRequest request){
			String parentId = request.getParameter("id");
			request.getSession().setAttribute("parentId", parentId);
			List<Address> objList = addressDAO.findAddressByEmployeeId(parentId);
			String objId = null;
			for(Address obj: objList){
				objId= obj.getEntId();
			}
			request.getSession().setAttribute("redirectSaveAddressURL","listEmployee.do");
			if(objId !=null && !"".equals(objId))
				return "redirect:editAddress.do?id="+objId;
			else
				return "redirect:newAddress.do";
		}
	

	}
	