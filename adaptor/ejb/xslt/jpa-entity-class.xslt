<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
  </xsl:template>
   
  <xsl:template match="entity">
	<xsl:param name="appname" />
	<xsl:variable name="filename" select="concat(@name,'.java')"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-EJB/ejbModule/com/mfu/entity/{$filename}" format="text">
	package com.mfu.entity;
	import java.io.Serializable;
	import java.util.Date;
	import java.util.Set;

	import javax.persistence.CascadeType;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.OneToMany;
	import javax.persistence.OneToOne;
	import javax.persistence.ManyToOne;
	import javax.persistence.Transient;

	@Entity
	public class <xsl:value-of select="@name"/> implements Serializable{
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long entId;
		public long getId() {
			return this.entId;
		}
		public void setId(long entId) {
			this.entId = entId;
		}

		<xsl:apply-templates select="attribute">
		<xsl:with-param name="entityname" select="@name"></xsl:with-param>
		</xsl:apply-templates>
	}
	</xsl:result-document>
  </xsl:template>

  <xsl:template match="attribute">
	<xsl:param name="entityname" />
	<xsl:variable name="datatype">
		<xsl:choose>		       
			<xsl:when test="@datatype='string'">String</xsl:when>
			<xsl:when test="@datatype='double'">double</xsl:when>
			<xsl:when test="@datatype='integer'">int</xsl:when>
			<xsl:when test="@datatype='date'">Date</xsl:when>
			<xsl:when test="@datatype='file'">String</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="@relationtype='1-n'"><xsl:text disable-output-escaping="yes"><![CDATA[Set<]]></xsl:text><xsl:value-of select="@datatype"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text></xsl:when>
					<xsl:otherwise><xsl:value-of select="@datatype"/></xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>	
	</xsl:variable>
	<xsl:variable name="attrname">
		 <xsl:call-template name="capitalizeFirstLetter">
	    		       <xsl:with-param name="str" select="@name"></xsl:with-param>
		 </xsl:call-template>
	</xsl:variable>
	// Generated code for attribute: <xsl:value-of select="@name"/>
	<xsl:choose>
	    <xsl:when test="@relationtype='1-1'">
		@OneToOne</xsl:when>
            <xsl:when test="@relationtype='1-n'">
		@OneToMany</xsl:when>
	    <xsl:when test="@relationtype='n-1'">
		@ManyToOne</xsl:when>
	    <xsl:when test="@relationtype='n-n'">
		@ManyToMany</xsl:when>
	</xsl:choose><xsl:if test="@parent='yes'">(mappedBy="<xsl:value-of select="lower-case($entityname)"/>", cascade={CascadeType.ALL})</xsl:if>
		private <xsl:value-of select="$datatype"/><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>;
		public <xsl:value-of select="$datatype"/> get<xsl:value-of select="$attrname"/>() {
			return <xsl:value-of select="@name"/>;
		}
		public void set<xsl:value-of select="$attrname"/>(<xsl:value-of select="$datatype"/> param) {
			this.<xsl:value-of select="@name"/> = param;
		}
	<xsl:if test="@datatype='file'">
		@Transient
		private Object<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>Data;
		public Object get<xsl:value-of select="$attrname"/>Data() {
			return <xsl:value-of select="@name"/>Data;
		}
		public void set<xsl:value-of select="$attrname"/>Data(Object param) {
			this.<xsl:value-of select="@name"/>Data = param;
		}
	</xsl:if>

  </xsl:template>

   <xsl:template name="capitalizeFirstLetter">
		    <xsl:param name="str" />
		    <xsl:value-of select="concat(upper-case(substring($str, 1, 1)),substring($str, 2))"/>
  </xsl:template>	
</xsl:stylesheet>