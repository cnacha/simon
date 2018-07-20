<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
	<xsl:variable name="appname" select="application/@name"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-WEB/src/com/mfu/web/util/DatePropertyEditor.java" format="text">
	package com.mfu.web.util;

	import java.beans.PropertyEditorSupport;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.Locale;

	public class DatePropertyEditor  extends PropertyEditorSupport {
		String format = "dd/MM/yyyy";

		Date defaultDate = new Date();

		public void setAsText(String textValue) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(format,new Locale("th", "TH"));
			if (textValue == null) {
				setValue(defaultDate);
				return;
			}
			Date retDate = defaultDate;
			try {
				retDate = dateFormatter.parse(textValue);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			setValue(retDate);
		}

		@Override
		public String getAsText() {
			Date date = (Date) getValue();
			SimpleDateFormat dateFormatter = new SimpleDateFormat(format,new Locale("th", "TH"));
			if(date == null)
				return "";
			else
			return dateFormatter.format(date);
		}

	}
	</xsl:result-document>
  </xsl:template>
   
  <xsl:template match="entity">
	<xsl:param name="appname" />
	<xsl:variable name="entityname" select="@name" />
	<xsl:variable name="filename" select="concat(@name,'Controller.java')"></xsl:variable>
	<xsl:variable name="isChildEntity">
		<xsl:for-each select="attribute" >
			<xsl:if test="@parent='no'"><xsl:text>yes</xsl:text></xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<xsl:result-document href="workspace/{$appname}-WEB/src/com/mfu/web/controller/{$filename}" format="text">
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
	public class <xsl:value-of select="@name"/>Controller {
		
		<xsl:value-of select="@name"/>DAO <xsl:value-of select="lower-case(@name)"/>DAO = new <xsl:value-of select="@name"/>DAO();
		
		public <xsl:value-of select="@name"/>Controller() {
			try {
				<xsl:value-of select="lower-case(@name)"/>DAO.init();
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

		//Generated method for listing <xsl:value-of select="@name"/>
		@RequestMapping("/list<xsl:value-of select="@name"/>")
		public ModelAndView list<xsl:value-of select="@name"/>(){
		
			ModelAndView mv = new ModelAndView("list<xsl:value-of select="@name"/>.jsp");
			
			<xsl:text><![CDATA[List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text><![CDATA[>]]></xsl:text> objList;
			try {
				objList = <xsl:value-of select="lower-case(@name)"/>DAO.getAllObject();
				mv.addObject("objList", objList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}

		@RequestMapping("/new<xsl:value-of select="@name"/>")
		public ModelAndView new<xsl:value-of select="@name"/>(HttpServletRequest request){
			ModelAndView mv = new ModelAndView("form<xsl:value-of select="@name"/>.jsp");
			<xsl:value-of select="@name"/> obj = new <xsl:value-of select="@name"/>();
			<xsl:for-each select="attribute" >
			     <xsl:if test="@parent='no' and ends-with(@relationtype,'-1')">
				<xsl:variable name="attrname">
					<xsl:call-template name="capitalizeFirstLetter">
						      <xsl:with-param name="str" select="@name"></xsl:with-param>
					</xsl:call-template>
				</xsl:variable>
			if(request.getSession().getAttribute("parentId")!=null)
				 obj.set<xsl:value-of select="$attrname"/>EntId((String)request.getSession().getAttribute("parentId"));
			     </xsl:if>
			</xsl:for-each>
			mv.addObject("obj", obj);
		
			return mv;
		}

		@RequestMapping("/save<xsl:value-of select="@name"/>")
		public String save<xsl:value-of select="@name"/>(
				@ModelAttribute("<xsl:value-of select="lower-case(@name)"/>") <xsl:value-of select="@name"/> obj, 
				BindingResult result, HttpServletRequest request){
			<xsl:for-each select="attribute" >
			     <xsl:if test="@datatype='file'">
				<xsl:variable name="attrname">
					<xsl:call-template name="capitalizeFirstLetter">
						      <xsl:with-param name="str" select="@name"></xsl:with-param>
					</xsl:call-template>
				</xsl:variable>
			CommonsMultipartFile filea = (CommonsMultipartFile) obj.get<xsl:value-of select="$attrname"/>Data();
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
						obj.set<xsl:value-of select="$attrname"/>(filename);
						System.out.println("save file at: " + fileloc);

					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				obj.set<xsl:value-of select="$attrname"/>Data(null);
			}
			     </xsl:if>
			</xsl:for-each>
			try {
				// obj is not existed, create it
				if(obj.getEntId() == null || "".equals(obj.getEntId())){
					obj.setEntId(null);
					<xsl:value-of select="lower-case(@name)"/>DAO.create(obj);
					
				// object is existed
				} else {
					<xsl:value-of select="lower-case(@name)"/>DAO.update(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			<xsl:if test="$isChildEntity='yes'">
			if(request.getSession().getAttribute("redirectSave<xsl:value-of select="@name"/>URL")!=null)
				return "redirect:"+request.getSession().getAttribute("redirectSave<xsl:value-of select="@name"/>URL");
			else
			</xsl:if>
				return "redirect:list<xsl:value-of select="@name"/>.do";
		}

		@RequestMapping("/delete<xsl:value-of select="@name"/>")
		public String delete<xsl:value-of select="@name"/>(HttpServletRequest request){
			<xsl:choose>
				<xsl:when test="$isChildEntity='yes'">
			<xsl:value-of select="lower-case(@name)"/>DAO.delete((String)request.getSession().getAttribute("parentId"), request.getParameter("id"));
				</xsl:when>
				<xsl:otherwise>
			<xsl:value-of select="lower-case(@name)"/>DAO.delete(request.getParameter("id"));
				</xsl:otherwise>
			</xsl:choose>
	
			<xsl:if test="$isChildEntity='yes'">
			if(request.getSession().getAttribute("redirectSave<xsl:value-of select="@name"/>URL")!=null)
				return "redirect:"+request.getSession().getAttribute("redirectSave<xsl:value-of select="@name"/>URL");
			else
			</xsl:if>
				return "redirect:list<xsl:value-of select="@name"/>.do";
		}

		@RequestMapping("/edit<xsl:value-of select="@name"/>")
		public ModelAndView edit<xsl:value-of select="@name"/>(HttpServletRequest request){
			String paramId = request.getParameter("id");
			
			<xsl:value-of select="@name"/> foundObj;
			ModelAndView mv = new ModelAndView("form<xsl:value-of select="@name"/>.jsp");
			try {
			<xsl:choose>
			<xsl:when test="$isChildEntity='yes'">
				foundObj = <xsl:value-of select="lower-case(@name)"/>DAO.getObjectById((String)request.getSession().getAttribute("parentId"), paramId);
			</xsl:when>
			<xsl:otherwise>
				foundObj = <xsl:value-of select="lower-case(@name)"/>DAO.getObjectById(paramId);
			</xsl:otherwise>
			</xsl:choose>
				mv.addObject("obj", foundObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mv;
		}

		<xsl:apply-templates select="attribute">
			<xsl:with-param name="entityname" select="@name"></xsl:with-param>
			<xsl:with-param name="appname" select="$appname"></xsl:with-param>
		</xsl:apply-templates>
<!--
		<xsl:apply-templates select="query">
			<xsl:with-param name="entityname" select="@name"></xsl:with-param>
		</xsl:apply-templates>
-->
	}
	</xsl:result-document>
	
  </xsl:template>

  <xsl:template match="attribute">
	<xsl:param name="appname" />
	<xsl:param name="entityname" />
	<xsl:if test="@parent='no' and @relationtype='n-1'">
			<xsl:variable name="attrname">
				 <xsl:call-template name="capitalizeFirstLetter">
					       <xsl:with-param name="str" select="@name"></xsl:with-param>
				 </xsl:call-template>
			</xsl:variable>
		@RequestMapping("/list<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>")
		public ModelAndView list<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>(HttpServletRequest request){
			String parentId = null;
			// check if pararameter passed
			if(request.getParameter("id")!= null){
				parentId =request.getParameter("id");
				request.getSession().setAttribute("parentId", parentId);
				request.getSession().setAttribute("redirectSave<xsl:value-of select="$entityname"/>URL","list<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>.do");
			} else {
				parentId = (String)request.getSession().getAttribute("parentId");
			}
			
			ModelAndView mv = new ModelAndView("list<xsl:value-of select="$entityname"/>.jsp");
			<xsl:text><![CDATA[List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[> objList;]]></xsl:text>
			try {
				objList = <xsl:value-of select="lower-case($entityname)"/>DAO.find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>Id(parentId);
				mv.addObject("objList", objList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}
	</xsl:if>
	<xsl:if test="@parent='no' and @relationtype='1-1'">
		<xsl:variable name="attrname">
			<xsl:call-template name="capitalizeFirstLetter">
				      <xsl:with-param name="str" select="@name"></xsl:with-param>
			</xsl:call-template>
		</xsl:variable>
		@RequestMapping("/edit<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>")
		public String edit<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>(HttpServletRequest request){
			String parentId = request.getParameter("id");
			request.getSession().setAttribute("parentId", parentId);
			<xsl:text><![CDATA[List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[>]]></xsl:text> objList = <xsl:value-of select="lower-case($entityname)"/>DAO.find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>Id(parentId);
			String objId = null;
			for(<xsl:value-of select="$entityname"/> obj: objList){
				objId= obj.getEntId();
			}
			request.getSession().setAttribute("redirectSave<xsl:value-of select="$entityname"/>URL","list<xsl:value-of select="$attrname"/>.do");
			<xsl:text><![CDATA[if(objId !=null && !"".equals(objId))]]></xsl:text>
				return "redirect:edit<xsl:value-of select="$entityname"/>.do?id="+objId;
			else
				return "redirect:new<xsl:value-of select="$entityname"/>.do";
		}
	</xsl:if>
 </xsl:template>

  <xsl:template match="query">
	<xsl:param name="entityname" />
	@RequestMapping("/<xsl:value-of select="@name"/>")
	public ModelAndView <xsl:value-of select="@name"/>(HttpServletRequest request){
		<xsl:for-each select="param" >
			<xsl:variable name="datatypeStr">
			<xsl:call-template name="getDataTypeName">
				       <xsl:with-param name="str" select="@datatype"></xsl:with-param>
			 </xsl:call-template>	
			</xsl:variable>
			<xsl:value-of select="$datatypeStr"/><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/> = <xsl:choose>
				<xsl:when test="@datatype='double'">
				<xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text>Double.parseDouble(request.getParameter("<xsl:value-of select="@name"/>"));
				</xsl:when>
				<xsl:when test="@datatype='integer'">
				<xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text>Integer.parseInt(request.getParameter("<xsl:value-of select="@name"/>"));
				</xsl:when>
				<xsl:otherwise>
				<xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text>request.getParameter("<xsl:value-of select="@name"/>");
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		ModelAndView mv = new ModelAndView("list<xsl:value-of select="$entityname"/>.jsp");
		
		try {
			<xsl:text><![CDATA[List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[>]]></xsl:text> objList = <xsl:value-of select="lower-case(@name)"/>DAO.<xsl:value-of select="@name"/>(<xsl:for-each select="param" >
				<xsl:if test="position()>1">
					<xsl:text disable-output-escaping="yes"><![CDATA[,]]></xsl:text>
				</xsl:if>
				<xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text><xsl:value-of select="@name"/></xsl:for-each>);
			mv.addObject("objList", objList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
  </xsl:template>

   <xsl:template name="capitalizeFirstLetter">
		    <xsl:param name="str" />
		    <xsl:value-of select="concat(upper-case(substring($str, 1, 1)),substring($str, 2))"/>
  </xsl:template>
  <xsl:template name="getDataTypeName">
		    <xsl:param name="str" />
		    <xsl:choose>		       
				<xsl:when test="$str='string'">String</xsl:when>
				<xsl:when test="$str='double'">double</xsl:when>
				<xsl:when test="$str='integer'">int</xsl:when>
		   </xsl:choose>
</xsl:template>	
</xsl:stylesheet>