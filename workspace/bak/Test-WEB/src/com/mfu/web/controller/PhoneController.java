
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
	public class PhoneController {
		
		PhoneDAO phoneDAO = new PhoneDAO();
		
		public PhoneController() {
			try {
				phoneDAO.init();
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

		//Generated method for listing Phone
		@RequestMapping("/listPhone")
		public ModelAndView listPhone(){
		
			ModelAndView mv = new ModelAndView("listPhone.jsp");
			
			List<Phone> objList;
			try {
				objList = phoneDAO.getAllObject();
				mv.addObject("objList", objList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}

		@RequestMapping("/newPhone")
		public ModelAndView newPhone(HttpServletRequest request){
			ModelAndView mv = new ModelAndView("formPhone.jsp");
			Phone obj = new Phone();
			
			if(request.getSession().getAttribute("parentId")!=null)
				 obj.setEmployeeEntId((String)request.getSession().getAttribute("parentId"));
			     
			mv.addObject("obj", obj);
		
			return mv;
		}

		@RequestMapping("/savePhone")
		public String savePhone(
				@ModelAttribute("phone") Phone obj, 
				BindingResult result, HttpServletRequest request){
			
			try {
				// obj is not existed, create it
				if(obj.getEntId() == null || "".equals(obj.getEntId())){
					obj.setEntId(null);
					phoneDAO.create(obj);
					
				// object is existed
				} else {
					phoneDAO.update(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(request.getSession().getAttribute("redirectSavePhoneURL")!=null)
				return "redirect:"+request.getSession().getAttribute("redirectSavePhoneURL");
			else
			
				return "redirect:listPhone.do";
		}

		@RequestMapping("/deletePhone")
		public String deletePhone(HttpServletRequest request){
			phoneDAO.delete((String)request.getSession().getAttribute("parentId"), request.getParameter("id"));
				
			if(request.getSession().getAttribute("redirectSavePhoneURL")!=null)
				return "redirect:"+request.getSession().getAttribute("redirectSavePhoneURL");
			else
			
				return "redirect:listPhone.do";
		}

		@RequestMapping("/editPhone")
		public ModelAndView editPhone(HttpServletRequest request){
			String paramId = request.getParameter("id");
			
			Phone foundObj;
			ModelAndView mv = new ModelAndView("formPhone.jsp");
			try {
			
				foundObj = phoneDAO.getObjectById((String)request.getSession().getAttribute("parentId"), paramId);
			
				mv.addObject("obj", foundObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mv;
		}

		
		@RequestMapping("/listPhoneByEmployee")
		public ModelAndView listPhoneByEmployee(HttpServletRequest request){
			String parentId = null;
			// check if pararameter passed
			if(request.getParameter("id")!= null){
				parentId =request.getParameter("id");
				request.getSession().setAttribute("parentId", parentId);
				request.getSession().setAttribute("redirectSavePhoneURL","listPhoneByEmployee.do");
			} else {
				parentId = (String)request.getSession().getAttribute("parentId");
			}
			
			ModelAndView mv = new ModelAndView("listPhone.jsp");
			List<Phone> objList;
			try {
				objList = phoneDAO.findPhoneByEmployeeId(parentId);
				mv.addObject("objList", objList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}
	

	}
	