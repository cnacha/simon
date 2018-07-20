<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
  </xsl:template>
   
  <xsl:template match="entity">
	<xsl:param name="appname" />
	<xsl:variable name="filename" select="concat(@name,'Service.java')"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-EJB/ejbModule/com/mfu/service/{$filename}" format="text">
	package com.mfu.service;

	import com.mfu.entity.*;
	import java.util.List;

	public interface <xsl:value-of select="@name"/>Service {

		// Genereated default methods for CRUD
		public void create(<xsl:value-of select="@name"/> param);
	
		public void update(<xsl:value-of select="@name"/> param);
	
		public void delete(long id);

		public <xsl:text><![CDATA[List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text><![CDATA[>]]></xsl:text> getAll<xsl:value-of select="@name"/>();

		public <xsl:value-of select="@name"/> find<xsl:value-of select="@name"/>ById(long id); 

		<xsl:apply-templates select="attribute">
			<xsl:with-param name="entityname" select="@name"></xsl:with-param>
		</xsl:apply-templates>

		<xsl:apply-templates select="query">
			<xsl:with-param name="entityname" select="@name"></xsl:with-param>
		</xsl:apply-templates>
	}
	</xsl:result-document>
  </xsl:template>

  <xsl:template match="attribute">
	<xsl:param name="entityname" />
	<xsl:choose>
	    <xsl:when test="@searchable='yes'">
		<xsl:variable name="datatype">
			<xsl:call-template name="getDataTypeName">
				       <xsl:with-param name="str" select="@datatype"></xsl:with-param>
			 </xsl:call-template>	
		</xsl:variable>
		<xsl:variable name="attrname">
			 <xsl:call-template name="capitalizeFirstLetter">
				       <xsl:with-param name="str" select="@name"></xsl:with-param>
			 </xsl:call-template>
		</xsl:variable>
		// Generated code for searching attribute: <xsl:value-of select="@name"/><xsl:text><![CDATA[
		]]></xsl:text><xsl:text><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[>]]></xsl:text> find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>(<xsl:value-of select="$datatype"/> param);
	   </xsl:when>
	   <xsl:when test="@parent='no'">
		<xsl:variable name="attrname">
			<xsl:call-template name="capitalizeFirstLetter">
				      <xsl:with-param name="str" select="@name"></xsl:with-param>
			</xsl:call-template>
		</xsl:variable>
		// Generated code for searching by parent relationship:
		<xsl:text><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[>]]></xsl:text> find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>Id(long id);
	   </xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template match="query">
	<xsl:param name="entityname" />
		// Generated code for query: <xsl:value-of select="@name"/><xsl:text><![CDATA[
		]]></xsl:text><xsl:text><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[> ]]></xsl:text><xsl:value-of select="@name"/>(<xsl:for-each select="param" >
		<xsl:if test="position()>1">
			<xsl:text disable-output-escaping="yes"><![CDATA[,]]></xsl:text>
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text>
		<xsl:call-template name="getDataTypeName">
				       <xsl:with-param name="str" select="@datatype"></xsl:with-param>
		</xsl:call-template>
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>
		</xsl:for-each> );

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