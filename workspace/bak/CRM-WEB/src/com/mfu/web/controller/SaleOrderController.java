
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
	public class SaleOrderController {
		
		SaleOrderDAO saleorderDAO = new SaleOrderDAO();
		
		public SaleOrderController() {
			try {
				saleorderDAO.init();
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

		//Generated method for listing SaleOrder
		@RequestMapping("/listSaleOrder")
		public ModelAndView listSaleOrder(){
		
			ModelAndView mv = new ModelAndView("listSaleOrder.jsp");
			
			List<SaleOrder> objList;
			try {
				objList = saleorderDAO.getAllObject();
				mv.addObject("objList", objList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}

		@RequestMapping("/newSaleOrder")
		public ModelAndView newSaleOrder(HttpServletRequest request){
			ModelAndView mv = new ModelAndView("formSaleOrder.jsp");
			SaleOrder obj = new SaleOrder();
			
			if(request.getSession().getAttribute("parentId")!=null)
				 obj.setCustomerEntId((String)request.getSession().getAttribute("parentId"));
			     
			mv.addObject("obj", obj);
		
			return mv;
		}

		@RequestMapping("/saveSaleOrder")
		public String saveSaleOrder(
				@ModelAttribute("saleorder") SaleOrder obj, 
				BindingResult result, HttpServletRequest request){
			
			try {
				// obj is not existed, create it
				if(obj.getEntId() == null || "".equals(obj.getEntId())){
					obj.setEntId(null);
					saleorderDAO.create(obj);
					
				// object is existed
				} else {
					saleorderDAO.update(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(request.getSession().getAttribute("redirectSaveSaleOrderURL")!=null)
				return "redirect:"+request.getSession().getAttribute("redirectSaveSaleOrderURL");
			else
			
				return "redirect:listSaleOrder.do";
		}

		@RequestMapping("/deleteSaleOrder")
		public String deleteSaleOrder(HttpServletRequest request){
			saleorderDAO.delete((String)request.getSession().getAttribute("parentId"), request.getParameter("id"));
				
			if(request.getSession().getAttribute("redirectSaveSaleOrderURL")!=null)
				return "redirect:"+request.getSession().getAttribute("redirectSaveSaleOrderURL");
			else
			
				return "redirect:listSaleOrder.do";
		}

		@RequestMapping("/editSaleOrder")
		public ModelAndView editSaleOrder(HttpServletRequest request){
			String paramId = request.getParameter("id");
			
			SaleOrder foundObj;
			ModelAndView mv = new ModelAndView("formSaleOrder.jsp");
			try {
			
				foundObj = saleorderDAO.getObjectById((String)request.getSession().getAttribute("parentId"), paramId);
			
				mv.addObject("obj", foundObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mv;
		}

		
		@RequestMapping("/listSaleOrderByCustomer")
		public ModelAndView listSaleOrderByCustomer(HttpServletRequest request){
			String parentId = null;
			// check if pararameter passed
			if(request.getParameter("id")!= null){
				parentId =request.getParameter("id");
				request.getSession().setAttribute("parentId", parentId);
				request.getSession().setAttribute("redirectSaveSaleOrderURL","listSaleOrderByCustomer.do");
			} else {
				parentId = (String)request.getSession().getAttribute("parentId");
			}
			
			ModelAndView mv = new ModelAndView("listSaleOrder.jsp");
			List<SaleOrder> objList;
			try {
				objList = saleorderDAO.findSaleOrderByCustomerId(parentId);
				mv.addObject("objList", objList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}
	

	}
	