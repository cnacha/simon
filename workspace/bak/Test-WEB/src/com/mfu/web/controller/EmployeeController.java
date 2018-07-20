
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
	public class EmployeeController {
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		public EmployeeController() {
			try {
				employeeDAO.init();
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

		//Generated method for listing Employee
		@RequestMapping("/listEmployee")
		public ModelAndView listEmployee(){
		
			ModelAndView mv = new ModelAndView("listEmployee.jsp");
			
			List<Employee> objList;
			try {
				objList = employeeDAO.getAllObject();
				mv.addObject("objList", objList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}

		@RequestMapping("/newEmployee")
		public ModelAndView newEmployee(HttpServletRequest request){
			ModelAndView mv = new ModelAndView("formEmployee.jsp");
			Employee obj = new Employee();
			
			mv.addObject("obj", obj);
		
			return mv;
		}

		@RequestMapping("/saveEmployee")
		public String saveEmployee(
				@ModelAttribute("employee") Employee obj, 
				BindingResult result, HttpServletRequest request){
			
			CommonsMultipartFile filea = (CommonsMultipartFile) obj.getPhotoData();
			if(filea !=null){
				InputStream inputStream = null;
				FileOutputStream outputStream = null;
				String filename = filea.getOriginalFilename();

				String fileloc = request.getRealPath("/filestore") + "\\"+ filename;
				try {
					if (filea.getSize() > 0) {
						inputStream = filea.getInputStream();
						outputStream = new FileOutputStream(fileloc);

						int readBytes = 0;
						byte[] buffer = new byte[8192];
						while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
							outputStream.write(buffer, 0, readBytes);
						}
						outputStream.close();
						inputStream.close();
						obj.setPhoto(filename);
						System.out.println("save file at: " + fileloc);

					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				obj.setPhotoData(null);
			}
			     
			try {
				// obj is not existed, create it
				if(obj.getEntId() == null || "".equals(obj.getEntId())){
					obj.setEntId(null);
					employeeDAO.create(obj);
					
				// object is existed
				} else {
					employeeDAO.update(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
				return "redirect:listEmployee.do";
		}

		@RequestMapping("/deleteEmployee")
		public String deleteEmployee(HttpServletRequest request){
			employeeDAO.delete(request.getParameter("id"));
				
				return "redirect:listEmployee.do";
		}

		@RequestMapping("/editEmployee")
		public ModelAndView editEmployee(HttpServletRequest request){
			String paramId = request.getParameter("id");
			
			Employee foundObj;
			ModelAndView mv = new ModelAndView("formEmployee.jsp");
			try {
			
				foundObj = employeeDAO.getObjectById(paramId);
			
				mv.addObject("obj", foundObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mv;
		}

		

	}
	