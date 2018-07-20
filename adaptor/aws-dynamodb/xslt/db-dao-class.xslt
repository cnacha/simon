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
	<xsl:result-document href="workspace/{$appname}-SERV/src/com/mfu/dao/{$filename}" format="text">
package com.mfu.dao;

import java.util.List;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.mfu.entity.*;
import com.mfu.util.AWSFacade;

public class <xsl:value-of select="@name"/>DAO {
	
	private static final String tableName = "<xsl:value-of select="@name"/>";
	private AWSFacade facade = null;
	private static DynamoDBMapper mapper = null;
	
	public void init() throws Exception{
		facade = new AWSFacade();
		AmazonDynamoDBClient client = facade.connect();
		mapper = new DynamoDBMapper(client);
		if(!facade.isTableAvailable(tableName))
			facade.createTable(tableName, 10L, 5L,  
		<xsl:if test="$isChildEntity='yes'">
			<xsl:for-each select="attribute" >
			<xsl:variable name="attrname">
				 <xsl:call-template name="capitalizeFirstLetter">
					       <xsl:with-param name="str" select="@name"></xsl:with-param>
				 </xsl:call-template>
			</xsl:variable>
			<xsl:if test="@relationtype ne '' and @parent='no'">
				"<xsl:value-of select="$attrname"/>EntId", "S", 
			</xsl:if>
			</xsl:for-each>
				
		</xsl:if>
				"EntId", "S");
	}
	
	public void reset() throws Exception{
		facade.deleteTable(tableName);
		init();
	}
	
	public void create(<xsl:value-of select="@name"/> obj){
		mapper.save(obj);
	}
	
	public void update(<xsl:value-of select="@name"/> obj){
		mapper.save(obj);
	}
	
	public void delete(<xsl:if test="$isChildEntity='yes'">String parentId, </xsl:if>String id){
		<xsl:value-of select="@name"/> obj = new <xsl:value-of select="@name"/>();
		obj.setEntId(id);

		<xsl:for-each select="attribute" >
			<xsl:variable name="attrname">
				 <xsl:call-template name="capitalizeFirstLetter">
					       <xsl:with-param name="str" select="@name"></xsl:with-param>
				 </xsl:call-template>
			</xsl:variable>
			<xsl:choose>
			<xsl:when test="@relationtype ne '' and @parent='yes'">
		//delete related <xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[
		]]></xsl:text><xsl:value-of select="$attrname"/>DAO <xsl:value-of select="lower-case(@name)"/>DAO = new <xsl:value-of select="$attrname"/>DAO();
		try {
			<xsl:value-of select="lower-case(@name)"/>DAO.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		<xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="$attrname"/><xsl:text disable-output-escaping="yes"><![CDATA[> ]]></xsl:text> <xsl:value-of select="lower-case(@name)"/>List = <xsl:value-of select="lower-case(@name)"/>DAO.find<xsl:value-of select="$attrname"/>By<xsl:value-of select="$entityname"/>Id(id);
		for(<xsl:value-of select="$attrname"/> cobj: <xsl:value-of select="lower-case(@name)"/>List){
			mapper.delete(cobj);
		}		
			</xsl:when>
			<xsl:when test="@relationtype ne '' and @parent='no'">
		obj.set<xsl:value-of select="$attrname"/>EntId(parentId);
			</xsl:when>
			</xsl:choose>
		</xsl:for-each>
		mapper.delete(obj);
		
	}
	
	<xsl:text disable-output-escaping="yes"><![CDATA[public List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> getAllObject(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		<xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> objList = mapper.scan(<xsl:value-of select="@name"/>.class, scanExpression);
		return objList;
	}
	
		<xsl:choose>
			<xsl:when test="$isChildEntity='yes'">
	public <xsl:value-of select="@name"/> getObjectById(String parentId, String id){
		Condition hashKeyCondition = new Condition().withComparisonOperator(
				ComparisonOperator.EQ.toString()).withAttributeValueList(
				new AttributeValue().withS(id));
		
		<xsl:value-of select="@name"/> obj = new <xsl:value-of select="@name"/>();
		<xsl:for-each select="attribute" >
			<xsl:variable name="attrname">
				 <xsl:call-template name="capitalizeFirstLetter">
					       <xsl:with-param name="str" select="@name"></xsl:with-param>
				 </xsl:call-template>
			</xsl:variable>
			<xsl:if test="@relationtype ne '' and @parent='no'">
		obj.set<xsl:value-of select="$attrname"/>EntId(parentId);
			</xsl:if>
		</xsl:for-each>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[DynamoDBQueryExpression<]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> queryExpression = new DynamoDBQueryExpression<xsl:text disable-output-escaping="yes"><![CDATA[<]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>()
				.withHashKeyValues(obj)
				.withRangeKeyCondition("EntId", hashKeyCondition);
				
		<xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> objList = mapper.query(<xsl:value-of select="@name"/>.class, queryExpression);
		<xsl:text disable-output-escaping="yes"><![CDATA[if(objList!=null && objList.size()>0)]]></xsl:text>
			return objList.get(0);
		else 
			return null;
	}
			</xsl:when>
			<xsl:otherwise>
	public <xsl:value-of select="@name"/> getObjectById(String id){
		<xsl:value-of select="@name"/> obj = mapper.load(<xsl:value-of select="@name"/>.class, id);
		return obj;
	}
			</xsl:otherwise>
		</xsl:choose>
	
	<xsl:for-each select="attribute" >
		<xsl:variable name="attrname">
			 <xsl:call-template name="capitalizeFirstLetter">
				       <xsl:with-param name="str" select="@name"></xsl:with-param>
			 </xsl:call-template>
		</xsl:variable>
		<xsl:if test="@relationtype ne '' and @parent='no'">
<xsl:text disable-output-escaping="yes"><![CDATA[public List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> find<xsl:value-of select="$entityname"/>By<xsl:value-of select="$attrname"/>Id(String parentId){
		<xsl:value-of select="$entityname"/> obj = new <xsl:value-of select="$entityname"/>();
		obj.set<xsl:value-of select="$attrname"/>EntId(parentId);
		<xsl:text disable-output-escaping="yes"><![CDATA[DynamoDBQueryExpression<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> queryExpression = new DynamoDBQueryExpression<xsl:text disable-output-escaping="yes"><![CDATA[<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>()
				.withHashKeyValues(obj);
		
		<xsl:text disable-output-escaping="yes"><![CDATA[List<]]></xsl:text><xsl:value-of select="$entityname"/><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text> objList = mapper.query(<xsl:value-of select="$entityname"/>.class, queryExpression);
		return objList;
	}
		</xsl:if>
	</xsl:for-each>

	

}
	</xsl:result-document>
  </xsl:template>

   <xsl:template name="capitalizeFirstLetter">
		    <xsl:param name="str" />
		    <xsl:value-of select="concat(upper-case(substring($str, 1, 1)),substring($str, 2))"/>
  </xsl:template>	
</xsl:stylesheet>