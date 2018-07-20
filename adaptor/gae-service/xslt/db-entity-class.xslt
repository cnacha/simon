<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
	<xsl:variable name="appname" select="application/@name"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-SERV/src/com/telabase/ds/OfyHelper.java" format="text">
	<xsl:text disable-output-escaping="yes"><![CDATA[
package com.telabase.ds;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;
import com.telabase.security.entity.User;

public class OfyHelper implements ServletContextListener{
	public void contextInitialized(ServletContextEvent event) {]]></xsl:text>
		ObjectifyService.register(User.class);
		<xsl:for-each select="application/data-model/entity" >
		ObjectifyService.register(<xsl:value-of select="@name"/>.class);
		</xsl:for-each>
	 <xsl:text disable-output-escaping="yes"><![CDATA[ 
	}

	  public void contextDestroyed(ServletContextEvent event) {
	    // App Engine does not currently invoke this method.
	  }
}

	]]></xsl:text>
	</xsl:result-document>
  </xsl:template>
   
  <xsl:template match="entity">
	<xsl:param name="appname" />
	<xsl:variable name="filename" select="concat(@name,'.java')"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-SERV/src/com/telabase/ds/entity/{$filename}" format="text">
	<xsl:variable name="isChildEntity">
	<xsl:for-each select="attribute" >
		<xsl:if test="@parent='no'"><xsl:text>yes</xsl:text></xsl:if>
	</xsl:for-each>
	</xsl:variable>
package com.telabase.ds.entity;

import java.util.*;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.telabase.json.formatter.*;
import com.telabase.ds.dao.*;

<xsl:choose>
<xsl:when test="@flexibleprop">
</xsl:when>
<xsl:otherwise>
@Entity
</xsl:otherwise>
</xsl:choose>
@JsonIgnoreProperties(ignoreUnknown = true)
public class <xsl:value-of select="@name"/> {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	<xsl:if test="@flexibleprop = 'yes'">
	<xsl:text disable-output-escaping="yes"><![CDATA[
	private HashMap<String, Double> props;
	public double geValue(String property){
		return props.get(property);
	}
	
	public void addValue(String s, Double d){
		props.put(s, d);
	}
	public HashMap<String, Double> getProps() {
		return this.props;
	}
	public void setProps(HashMap<String, Double> props) {
		this.props = props;
	}
	]]></xsl:text>
	</xsl:if>
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
			<xsl:when test="@datatype='datetime'">Date</xsl:when>
			<xsl:when test="@datatype='file'">String</xsl:when>
		</xsl:choose>	
	</xsl:variable>
	<xsl:variable name="attrname">
		 <xsl:call-template name="capitalizeFirstLetter">
	    		       <xsl:with-param name="str" select="@name"></xsl:with-param>
		 </xsl:call-template>
	</xsl:variable>
	
	<xsl:choose>
	    <xsl:when test="@relationtype ne '' and @parent='yes'">
	    </xsl:when>	

	    <xsl:when test="@relationtype ne '' and @parent='no'">
	// Generated code for attribute: <xsl:value-of select="@name"/>
	@Index
	private long<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>Id;
	public long get<xsl:value-of select="$attrname"/>Id() {
		return <xsl:value-of select="@name"/>Id;
	}
	public void set<xsl:value-of select="$attrname"/>Id(long param) {
		this.<xsl:value-of select="@name"/>Id = param;
	}
			<xsl:if test="@eagerfetch = 'yes'">
	public <xsl:value-of select="$attrname"/> get<xsl:value-of select="$attrname"/>() {
		<xsl:value-of select="$attrname"/>DAO dao = new <xsl:value-of select="$attrname"/>DAO();
		return dao.findById(this.<xsl:value-of select="@name"/>Id);
	}
			</xsl:if>
	    </xsl:when>	

	    <xsl:otherwise>
	
	<xsl:if test="@datatype ne 'file'">
	<xsl:if test="@datatype='date'">
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)</xsl:if>
	<xsl:if test="@datatype='datetime'">
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)</xsl:if>
	<xsl:if test="@searchable='yes'">
	@Index</xsl:if>
	<xsl:choose>
	    <xsl:when test="@defaultvalue ne ''">
	private <xsl:value-of select="$datatype"/><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/> = <xsl:value-of select="@defaultvalue"/>;
		</xsl:when>	
		<xsl:otherwise> 
	private <xsl:value-of select="$datatype"/><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>;
	    </xsl:otherwise>
	</xsl:choose>   
	public <xsl:value-of select="$datatype"/> get<xsl:value-of select="$attrname"/>() {
		return <xsl:value-of select="@name"/>;
	}
	public void set<xsl:value-of select="$attrname"/>(<xsl:value-of select="$datatype"/> param) {
		this.<xsl:value-of select="@name"/> = param;
	}
	</xsl:if>
	<xsl:if test="@datatype='file'">
	private String<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>FilePath;
	public String get<xsl:value-of select="$attrname"/>FilePath() {
		return <xsl:value-of select="@name"/>FilePath;
	}
	public void set<xsl:value-of select="$attrname"/>FilePath(String param) {
		this.<xsl:value-of select="@name"/>FilePath = param;
	}
	</xsl:if>
	</xsl:otherwise>	
	</xsl:choose>

  </xsl:template>

   <xsl:template name="capitalizeFirstLetter">
		    <xsl:param name="str" />
		    <xsl:value-of select="concat(upper-case(substring($str, 1, 1)),substring($str, 2))"/>
  </xsl:template>	
</xsl:stylesheet>