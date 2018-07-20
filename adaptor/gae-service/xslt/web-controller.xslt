<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
	<xsl:variable name="appname" select="application/@name"></xsl:variable>
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
	<xsl:result-document href="workspace/{$appname}-SERV/src/com/telabase/ws/controller/{$filename}" format="text">
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
	public class <xsl:value-of select="@name"/>Controller {
		
		private final static Logger logger = Logger.getLogger(<xsl:value-of select="@name"/>Controller.class.getName());
		
	<xsl:choose>
	<xsl:when test="@flexibleprop = 'yes'">
		<xsl:text disable-output-escaping="yes"><![CDATA[@RequestMapping(value = "/api/]]></xsl:text><xsl:value-of select="lower-case(@name)"/><xsl:text disable-output-escaping="yes"><![CDATA[/save", method = RequestMethod.POST)]]></xsl:text>
		@ResponseBody
		public WSResponse save(@RequestBody <xsl:value-of select="@name"/> o, HttpServletRequest request) {
			try {
				<xsl:value-of select="@name"/>DAO dao = new <xsl:value-of select="@name"/>DAO();
				dao.insert(o);

			} catch (Exception e) {
				logger.warning(e.getMessage());
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
				
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}

	</xsl:when>
	<xsl:otherwise>
		<xsl:text disable-output-escaping="yes"><![CDATA[@RequestMapping(value = "/api/]]></xsl:text><xsl:value-of select="lower-case(@name)"/><xsl:text disable-output-escaping="yes"><![CDATA[/list", method = RequestMethod.GET)]]></xsl:text>
		@ResponseBody
		<xsl:text disable-output-escaping="yes"><![CDATA[public List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[> list(HttpServletRequest request) {]]></xsl:text>
		
			<xsl:value-of select="@name"/>DAO serve = new <xsl:value-of select="@name"/>DAO();
			return serve.list();
		}
		
		<xsl:text disable-output-escaping="yes"><![CDATA[@RequestMapping(value = "/api/]]></xsl:text><xsl:value-of select="lower-case(@name)"/><xsl:text disable-output-escaping="yes"><![CDATA[/save", method = RequestMethod.POST)]]></xsl:text>
		@ResponseBody
		public WSResponse save(@RequestBody <xsl:value-of select="@name"/> o, HttpServletRequest request) {

			<xsl:value-of select="@name"/>DAO serve = new <xsl:value-of select="@name"/>DAO();
			try {
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		<xsl:text disable-output-escaping="yes"><![CDATA[@RequestMapping(value = "/api/]]></xsl:text><xsl:value-of select="lower-case(@name)"/><xsl:text disable-output-escaping="yes"><![CDATA[/get", method = RequestMethod.GET)]]></xsl:text>
		@ResponseBody
		public <xsl:value-of select="@name"/> findById(HttpServletRequest request) {

			<xsl:value-of select="@name"/>DAO serve = new <xsl:value-of select="@name"/>DAO();
			<xsl:value-of select="@name"/> o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		<xsl:text disable-output-escaping="yes"><![CDATA[@RequestMapping(value = "/api/]]></xsl:text><xsl:value-of select="lower-case(@name)"/><xsl:text disable-output-escaping="yes"><![CDATA[/delete", method = RequestMethod.GET)]]></xsl:text>
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			<xsl:value-of select="@name"/>DAO serve = new <xsl:value-of select="@name"/>DAO();
			try {
				<xsl:value-of select="@name"/> o = new <xsl:value-of select="@name"/>();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
	</xsl:otherwise>
	</xsl:choose>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[@RequestMapping(value = "/admin/]]></xsl:text><xsl:value-of select="lower-case(@name)"/><xsl:text disable-output-escaping="yes"><![CDATA[/batch/save", method = RequestMethod.POST)]]></xsl:text>
		@ResponseBody
		<xsl:text disable-output-escaping="yes"><![CDATA[public List<WSResponse>]]></xsl:text> saveAsBatch(@RequestBody <xsl:value-of select="@name"/>[] list, HttpServletRequest request){
			<xsl:text disable-output-escaping="yes"><![CDATA[List<WSResponse> resList = new ArrayList<WSResponse>();]]></xsl:text>
			String result;
			for (<xsl:value-of select="@name"/> d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		<xsl:apply-templates select="attribute">
			<xsl:with-param name="entityname" select="@name"></xsl:with-param>
		</xsl:apply-templates>
	}
	
	
	</xsl:result-document>
	
  </xsl:template>

  <xsl:template match="attribute">
	<xsl:param name="appname" />
	<xsl:param name="entityname" />
	<xsl:if test="@parent='no' and @relationtype ne ''">
			<xsl:variable name="attrname">
				 <xsl:call-template name="capitalizeFirstLetter">
					       <xsl:with-param name="str" select="@name"></xsl:with-param>
				 </xsl:call-template>
			</xsl:variable>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[@RequestMapping(value = "/api/]]></xsl:text><xsl:value-of select="lower-case(@name)"/>/<xsl:value-of select="lower-case($entityname)"/><xsl:text disable-output-escaping="yes"><![CDATA[/list", method = RequestMethod.GET)]]></xsl:text>
		@ResponseBody
		<xsl:text disable-output-escaping="yes"><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[> ]]></xsl:text>listBy<xsl:value-of select="$attrname"/>(HttpServletRequest request) {
		
			<xsl:text disable-output-escaping="yes"><![CDATA[long id = Long.parseLong(request.getParameter("]]></xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[id"));
			]]></xsl:text>
			<xsl:value-of select="$entityname"/>DAO dao = new <xsl:value-of select="$entityname"/>DAO();
			<xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[> rs = dao.find]]></xsl:text><xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>Id(id);

			return rs;
		}

	</xsl:if>
	
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