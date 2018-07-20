<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
  </xsl:template>
   
  <xsl:template match="entity">
	<xsl:param name="appname" />
	<xsl:variable name="entityname"><xsl:value-of select="@name"/></xsl:variable>
	<xsl:variable name="filename" select="concat(@name,'ServiceBean.java')"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-EJB/ejbModule/com/mfu/service/{$filename}" format="text">
	package com.mfu.service;

	import com.mfu.entity.*;
	import java.util.List;
	import javax.ejb.Remote;
	import javax.ejb.Stateless;
	import javax.persistence.EntityManager;
	import javax.persistence.PersistenceContext;

	@Stateless
	@Remote(<xsl:value-of select="@name"/>Service.class)
	public class <xsl:value-of select="@name"/>ServiceBean implements <xsl:value-of select="@name"/>Service{

		@PersistenceContext(unitName = "app-database")
		EntityManager em;

		// Genereated default methods for CRUD
		@Override
		public void create(<xsl:value-of select="@name"/> param){
			em.persist(param);
		}
		
		@Override
		public void update(<xsl:value-of select="@name"/> param){
			em.merge(param);
		}
		
		@Override
		public void delete(long id){
			<xsl:value-of select="@name"/> obj = find<xsl:value-of select="@name"/>ById(id);
			if(obj!=null){
			<xsl:for-each select="attribute" >
				<xsl:if test="@parent='no'">
				<xsl:variable name="relname">
					<xsl:call-template name="capitalizeFirstLetter">
					<xsl:with-param name="str" select="@name"></xsl:with-param>
					</xsl:call-template>
				</xsl:variable>
				// destroy relationship to <xsl:value-of select="$relname"/>
				<xsl:text><![CDATA[
				List<]]></xsl:text><xsl:value-of select="$relname"/><xsl:text><![CDATA[>]]></xsl:text> objList = em.createQuery("SELECT obj.<xsl:value-of select="@name"/> FROM <xsl:value-of select="$entityname"/> obj WHERE obj.id='"+id+"'").getResultList();
				for(<xsl:value-of select="$relname"/> rel: objList){
					<xsl:choose>
						<xsl:when test="@relationtype='1-1'">
					rel.set<xsl:value-of select="$entityname"/>(null);
						</xsl:when>
						<xsl:otherwise>
					rel.get<xsl:value-of select="$entityname"/>().remove(obj);
						</xsl:otherwise>
					</xsl:choose>
					em.merge(rel);
				}</xsl:if></xsl:for-each>
				em.remove(obj);
			}
		}

		@Override
		public <xsl:value-of select="@name"/> find<xsl:value-of select="@name"/>ById(long id){
			<xsl:value-of select="@name"/> obj = em.find(<xsl:value-of select="@name"/>.class, id);
			return obj;
		}
		@Override
		public <xsl:text><![CDATA[List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text><![CDATA[>]]></xsl:text> getAll<xsl:value-of select="@name"/>(){
			return em.createQuery("SELECT ent FROM <xsl:value-of select="$entityname"/> ent").getResultList();
		}

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
		// Generated code for query by attribute: <xsl:value-of select="@name"/><xsl:text><![CDATA[
		@Override
		]]></xsl:text><xsl:text><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[>]]></xsl:text> find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>(<xsl:value-of select="$datatype"/> param){
			return em.createQuery("SELECT ent FROM <xsl:value-of select="$entityname"/> ent WHERE ent.<xsl:value-of select="@name"/> LIKE :pm").setParameter("pm", "%"+param+"%").getResultList();
		}
	   </xsl:when>
	   <xsl:when test="@parent='no'">
		<xsl:variable name="attrname">
			<xsl:call-template name="capitalizeFirstLetter">
				      <xsl:with-param name="str" select="@name"></xsl:with-param>
			</xsl:call-template>
		</xsl:variable>
	        // Generated code for searching by parent relationship:
		<xsl:text><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[>]]></xsl:text> find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>Id(long id){
			return em.createQuery("SELECT ent FROM <xsl:value-of select="$entityname"/> ent WHERE ent.<xsl:value-of select="@name"/>.id = :pm").setParameter("pm", id).getResultList();
		}
	   </xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template match="query">
	<xsl:param name="entityname" />
		// Generated code for query: <xsl:value-of select="@name"/><xsl:text><![CDATA[
		@Override
		]]></xsl:text><xsl:text><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text><![CDATA[> ]]></xsl:text><xsl:value-of select="@name"/>(<xsl:for-each select="param" >
		<xsl:if test="position()>1">
			<xsl:text disable-output-escaping="yes"><![CDATA[,]]></xsl:text>
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text>
		<xsl:call-template name="getDataTypeName">
				       <xsl:with-param name="str" select="@datatype"></xsl:with-param>
		</xsl:call-template>
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>
		</xsl:for-each> ){
			return em.createQuery("SELECT ent FROM <xsl:value-of select="$entityname"/> ent WHERE <xsl:value-of select="@statement"/>")
			<xsl:for-each select="param" >
				<xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text>.setParameter("<xsl:value-of select="@name"/>", <xsl:value-of select="@name"/><xsl:if test="@datatype='string'">+"%"</xsl:if>)
			</xsl:for-each><xsl:text disable-output-escaping="yes"><![CDATA[.getResultList();]]></xsl:text>
		
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