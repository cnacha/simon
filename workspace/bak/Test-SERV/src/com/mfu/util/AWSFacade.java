
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
	