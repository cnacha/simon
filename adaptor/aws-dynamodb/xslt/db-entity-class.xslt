<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
	<xsl:variable name="appname" select="application/@name"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-SERV/src/com/mfu/util/AWSFacade.java" format="text">
	<xsl:text disable-output-escaping="yes"><![CDATA[
package com.mfu.util;

import java.util.ArrayList;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

public class AWSFacade {
	private AmazonDynamoDBClient client = null;

	public AmazonDynamoDBClient connect() throws Exception {
		AWSCredentials credentials = new PropertiesCredentials(
				AWSFacade.class
						.getResourceAsStream("AwsCredentials.properties"));
		client = new AmazonDynamoDBClient(credentials);
	//	Region region = Region.getRegion(Regions.AP_SOUTHEAST_1);
	//	client.setRegion(region);
		client.setEndpoint("http://localhost:8089");
		return client;
	}
	
	public void deleteTable(String tableName){
		DeleteTableRequest deleteTableRequest = new DeleteTableRequest()
		.withTableName(tableName);
		DeleteTableResult result = client.deleteTable(deleteTableRequest);
	}

	public void createTable(String tableName, long readCapacityUnits,
			long writeCapacityUnits, String hashKeyName, String hashKeyType) {
		createTable(tableName, readCapacityUnits, writeCapacityUnits,
				hashKeyName, hashKeyType, null, null);
	}

	public void createTable(String tableName, long readCapacityUnits,
			long writeCapacityUnits, String hashKeyName, String hashKeyType,
			String rangeKeyName, String rangeKeyType) {
		try {
			System.out.println("Creating table " + tableName);
			ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			ks.add(new KeySchemaElement().withAttributeName(hashKeyName)
					.withKeyType(KeyType.HASH));
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName(hashKeyName).withAttributeType(
							hashKeyType));
			if (rangeKeyName != null) {
				ks.add(new KeySchemaElement().withAttributeName(rangeKeyName)
						.withKeyType(KeyType.RANGE));
				attributeDefinitions.add(new AttributeDefinition()
						.withAttributeName(rangeKeyName).withAttributeType(
								rangeKeyType));
			}
			// Provide initial provisioned throughput values as Java long data types
			ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput()
					.withReadCapacityUnits(readCapacityUnits)
					.withWriteCapacityUnits(writeCapacityUnits);
			CreateTableRequest request = new CreateTableRequest()
					.withTableName(tableName).withKeySchema(ks)
					.withProvisionedThroughput(provisionedthroughput);

			request.setAttributeDefinitions(attributeDefinitions);
			CreateTableResult result = client.createTable(request);

			waitForTableToBecomeAvailable(tableName);
		} catch (AmazonServiceException ase) {
			System.err.println("Failed to create table " + tableName + " "
					+ ase);
		}
	}

	public boolean isTableAvailable(String tableName) {
		String lastEvaluatedTableName = null;
		do {
			ListTablesRequest listTablesRequest = new ListTablesRequest()
					.withLimit(10).withExclusiveStartTableName(
							lastEvaluatedTableName);
			ListTablesResult result = client.listTables(listTablesRequest);
			lastEvaluatedTableName = result.getLastEvaluatedTableName();
			for (String name : result.getTableNames()) {
				if(tableName.equals(name))
					return true;
			}
		} while (lastEvaluatedTableName != null);
		return false;
	}

	private void waitForTableToBecomeAvailable(String tableName) {
		System.out.println("Waiting for " + tableName + " to become ACTIVE...");

		long startTime = System.currentTimeMillis();
		long endTime = startTime + (10 * 60 * 1000);
		while (System.currentTimeMillis() < endTime) {
			try {
				Thread.sleep(1000 * 20);
			} catch (Exception e) {
			}
			try {
				DescribeTableRequest request = new DescribeTableRequest()
						.withTableName(tableName);
				TableDescription tableDescription = client.describeTable(
						request).getTable();
				String tableStatus = tableDescription.getTableStatus();
				System.out.println("  - current state: " + tableStatus);
				if (tableStatus.equals(TableStatus.ACTIVE.toString()))
					return;
			} catch (AmazonServiceException ase) {
				if (ase.getErrorCode().equalsIgnoreCase(
						"ResourceNotFoundException") == false)
					throw ase;
			}
		}

		throw new RuntimeException("Table " + tableName + " never went active");
	}
}
	]]></xsl:text>
	</xsl:result-document>
  </xsl:template>
   
  <xsl:template match="entity">
	<xsl:param name="appname" />
	<xsl:variable name="filename" select="concat(@name,'.java')"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-SERV/src/com/mfu/entity/{$filename}" format="text">
	<xsl:variable name="isChildEntity">
	<xsl:for-each select="attribute" >
		<xsl:if test="@parent='no'"><xsl:text>yes</xsl:text></xsl:if>
	</xsl:for-each>
	</xsl:variable>
package com.mfu.entity;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "<xsl:value-of select="@name"/>")
public class <xsl:value-of select="@name"/> {

	private String entId;
	<xsl:choose>
		<xsl:when test="$isChildEntity='yes'">
	@DynamoDBRangeKey(attributeName = "EntId")
		</xsl:when>
		<xsl:otherwise>
	@DynamoDBHashKey(attributeName = "EntId")
		</xsl:otherwise>
	</xsl:choose>
<xsl:text disable-output-escaping="yes"><![CDATA[]]></xsl:text>@DynamoDBAutoGeneratedKey
	public String getEntId() {
		return entId;
	}
	public void setEntId(String entId) {
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
		</xsl:choose>	
	</xsl:variable>
	<xsl:variable name="attrname">
		 <xsl:call-template name="capitalizeFirstLetter">
	    		       <xsl:with-param name="str" select="@name"></xsl:with-param>
		 </xsl:call-template>
	</xsl:variable>
	// Generated code for attribute: <xsl:value-of select="@name"/>
	<xsl:choose>
	    <xsl:when test="@relationtype ne '' and @parent='yes'">
	    </xsl:when>	

	    <xsl:when test="@relationtype ne '' and @parent='no'">
	private String<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>EntId;
	@DynamoDBHashKey(attributeName = "<xsl:value-of select="$attrname"/>EntId")
	public String get<xsl:value-of select="$attrname"/>EntId() {
		return <xsl:value-of select="@name"/>EntId;
	}
	public void set<xsl:value-of select="$attrname"/>EntId(String param) {
		this.<xsl:value-of select="@name"/>EntId = param;
	}
	    </xsl:when>	

	    <xsl:otherwise>
	private <xsl:value-of select="$datatype"/><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>;
	@DynamoDBAttribute(attributeName = "<xsl:value-of select="$attrname"/>")
	public <xsl:value-of select="$datatype"/> get<xsl:value-of select="$attrname"/>() {
		return <xsl:value-of select="@name"/>;
	}
	public void set<xsl:value-of select="$attrname"/>(<xsl:value-of select="$datatype"/> param) {
		this.<xsl:value-of select="@name"/> = param;
	}
		<xsl:if test="@datatype='file'">
	private Object<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text><xsl:value-of select="@name"/>Data;
	public Object get<xsl:value-of select="$attrname"/>Data() {
		return <xsl:value-of select="@name"/>Data;
	}
	public void set<xsl:value-of select="$attrname"/>Data(Object param) {
		this.<xsl:value-of select="@name"/>Data = param;
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