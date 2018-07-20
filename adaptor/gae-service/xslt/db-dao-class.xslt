<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
  </xsl:template>
   
  <xsl:template match="entity">
	<xsl:param name="appname" />
	<xsl:variable name="entityname" select="@name" />
	<xsl:variable name="filename" select="concat(@name,'DAO.java')"></xsl:variable>
	<xsl:variable name="isChildEntity">
	<xsl:for-each select="attribute" >
		<xsl:if test="@parent='no'"><xsl:text>yes</xsl:text></xsl:if>
	</xsl:for-each>
	</xsl:variable>
	<xsl:result-document href="workspace/{$appname}-SERV/src/com/telabase/ds/dao/{$filename}" format="text">
package com.telabase.ds.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

import java.util.*;


public class <xsl:value-of select="@name"/>DAO {
	
	<xsl:choose>
	<xsl:when test="@flexibleprop = 'yes'">
	protected static String ENTITY_NAME = "<xsl:value-of select="@name"/>";
	
	protected DatastoreService ds;
	public <xsl:value-of select="@name"/>DAO() {
		ds = DatastoreServiceFactory.getDatastoreService();
	}
	public <xsl:value-of select="@name"/> convert(Entity entity){
		<xsl:text disable-output-escaping="yes"><![CDATA[
		if(entity!=null){
			
			Map<String, Object> propMap = entity.getProperties();
			Iterator propSet = propMap.keySet().iterator();
			HashMap<String, Double> measuresMap = new HashMap<String, Double>();
			String propName;
			Double propValue;
			while(propSet.hasNext()){
				propName = (String)propSet.next();
				if(!propName.startsWith("prop_"))
					continue;
				propValue = (Double)propMap.get(propName);
				measuresMap.put(propName.substring(5), propValue);
			}
			]]></xsl:text>
			<xsl:value-of select="@name"/> obj = new <xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[();
			obj.setId(entity.getKey().getId());
			obj.setProps(measuresMap);
			]]></xsl:text>
			<xsl:for-each select="attribute" >
			<xsl:variable name="attrname">
				 <xsl:call-template name="capitalizeFirstLetter">
						   <xsl:with-param name="str" select="@name"></xsl:with-param>
				 </xsl:call-template>
			</xsl:variable>
			<xsl:choose>
			<xsl:when test="@relationtype ne '' and @parent='no'">
		<xsl:text disable-output-escaping="yes"><![CDATA[obj.set]]></xsl:text><xsl:value-of select="$attrname"/>Id<xsl:text disable-output-escaping="yes"><![CDATA[((long) entity.getProperty("]]></xsl:text><xsl:value-of select="@name"/>Id<xsl:text disable-output-escaping="yes"><![CDATA["));
		]]></xsl:text>
			</xsl:when>
			<xsl:otherwise>
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
		<xsl:text disable-output-escaping="yes"><![CDATA[	obj.set]]></xsl:text><xsl:value-of select="$attrname"/><xsl:text disable-output-escaping="yes"><![CDATA[((]]></xsl:text><xsl:value-of select="$datatype"/><xsl:text disable-output-escaping="yes"><![CDATA[) entity.getProperty("]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA["));
		]]></xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:for-each>
			<xsl:text disable-output-escaping="yes"><![CDATA[
			return obj;
		} else
			return null;
	}
	]]></xsl:text>
	<xsl:text disable-output-escaping="yes"><![CDATA[public void insert(]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[ v) {]]></xsl:text>
		Entity e = new Entity(ENTITY_NAME);
		<xsl:for-each select="attribute" >
			<xsl:variable name="attrname">
				 <xsl:call-template name="capitalizeFirstLetter">
						   <xsl:with-param name="str" select="@name"></xsl:with-param>
				 </xsl:call-template>
			</xsl:variable>
		<xsl:choose>
			<xsl:when test="@relationtype ne '' and @parent='no'">
		<xsl:text disable-output-escaping="yes"><![CDATA[e.setProperty("]]></xsl:text><xsl:value-of select="@name"/>Id<xsl:text disable-output-escaping="yes"><![CDATA[", v.get]]></xsl:text><xsl:value-of select="$attrname"/><xsl:text disable-output-escaping="yes"><![CDATA[Id());
		]]></xsl:text>
			</xsl:when>
			<xsl:otherwise >
		<xsl:text disable-output-escaping="yes"><![CDATA[e.setProperty("]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[", v.get]]></xsl:text><xsl:value-of select="$attrname"/><xsl:text disable-output-escaping="yes"><![CDATA[());
		]]></xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:for-each>
		<xsl:text disable-output-escaping="yes"><![CDATA[
		HashMap<String, Double> map = v.getProps();
		for(String key : map.keySet()) {
			e.setProperty("prop_"+key, map.get(key));
		}
		ds.put(e);
		]]></xsl:text>
	}
	<xsl:for-each select="attribute" >
			<xsl:variable name="attrname">
				 <xsl:call-template name="capitalizeFirstLetter">
						   <xsl:with-param name="str" select="@name"></xsl:with-param>
				 </xsl:call-template>
			</xsl:variable>
		<xsl:choose>
			<xsl:when test="@relationtype ne '' and @parent='no'">
	public <xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>Id(long id) {
		<xsl:text disable-output-escaping="yes"><![CDATA[Filter filter = new FilterPredicate("]]></xsl:text><xsl:value-of select="@name"/>Id<xsl:text disable-output-escaping="yes"><![CDATA[", FilterOperator.EQUAL, id);]]></xsl:text>
		Query q = new Query(ENTITY_NAME).setFilter(filter);
		<xsl:text disable-output-escaping="yes"><![CDATA[List<Entity> result = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		]]></xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[
		List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[> list = new ArrayList<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>();
		for(Entity e: result) {
			list.add(convert(e));
		}
		
		return list;
		]]></xsl:text>
	}
			</xsl:when>
			<xsl:otherwise >
			</xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>
	</xsl:when>
	<xsl:otherwise>
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(<xsl:value-of select="@name"/>.class) 
		          .count();
	}
	
	<xsl:text disable-output-escaping="yes"><![CDATA[public List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> list(){
		<xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> results = ObjectifyService.ofy()
		          .load()
		          .type(<xsl:value-of select="@name"/>.class) 
		          .list();

		return results;
	}
	
	public void save(<xsl:value-of select="@name"/> o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(<xsl:value-of select="@name"/>.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public <xsl:value-of select="@name"/> findById(long id){
		return ObjectifyService.ofy().load().type(<xsl:value-of select="@name"/>.class).id(id).now();
	}

	public void delete(<xsl:value-of select="@name"/> o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	<xsl:for-each select="attribute" >
		<xsl:variable name="attrname">
			 <xsl:call-template name="capitalizeFirstLetter">
				       <xsl:with-param name="str" select="@name"></xsl:with-param>
			 </xsl:call-template>
		</xsl:variable>
		<xsl:if test="@searchable = 'yes' and @datatype = 'string'">
		<xsl:text disable-output-escaping="yes"><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> searchBy<xsl:value-of select="$attrname"/>(String keyword) {
			<xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> results = ObjectifyService.ofy()
					  .load()
					  .type(<xsl:value-of select="$entityname"/>.class)
					  <xsl:text disable-output-escaping="yes"><![CDATA[.filter("]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[ >=", keyword)]]></xsl:text>
					  <xsl:text disable-output-escaping="yes"><![CDATA[.filter("]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[ <", keyword + "\uFFFD")]]></xsl:text>
					  .list();

			return results;
		}
		</xsl:if>
		<xsl:if test="@relationtype ne '' and @parent='no'">
<xsl:text disable-output-escaping="yes"><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>Id(long <xsl:value-of select="@name"/>Id){
		<xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> results = ObjectifyService.ofy()
		          .load()
		          .type(<xsl:value-of select="$entityname"/>.class)
		          <xsl:text disable-output-escaping="yes"><![CDATA[.filter("]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[Id"]]></xsl:text>, <xsl:value-of select="@name"/>Id)
		          .list();

		return results;
	}
		</xsl:if>
	</xsl:for-each>
	
	</xsl:otherwise>
	</xsl:choose>


}
	</xsl:result-document>
  </xsl:template>

   <xsl:template name="capitalizeFirstLetter">
		    <xsl:param name="str" />
		    <xsl:value-of select="concat(upper-case(substring($str, 1, 1)),substring($str, 2))"/>
  </xsl:template>	
</xsl:stylesheet>