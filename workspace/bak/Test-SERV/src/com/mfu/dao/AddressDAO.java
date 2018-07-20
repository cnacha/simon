
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

public class AddressDAO {
	
	private static final String tableName = "Address";
	private AWSFacade facade = null;
	private static DynamoDBMapper mapper = null;
	
	public void init() throws Exception{
		facade = new AWSFacade();
		AmazonDynamoDBClient client = facade.connect();
		mapper = new DynamoDBMapper(client);
		if(!facade.isTableAvailable(tableName))
			facade.createTable(tableName, 10L, 5L,  
		
				"EmployeeEntId", "S", 
			
				"EntId", "S");
	}
	
	public void reset() throws Exception{
		facade.deleteTable(tableName);
		init();
	}
	
	public void create(Address obj){
		mapper.save(obj);
	}
	
	public void update(Address obj){
		mapper.save(obj);
	}
	
	public void delete(String parentId, String id){
		Address obj = new Address();
		obj.setEntId(id);

		
		obj.setEmployeeEntId(parentId);
			
		mapper.delete(obj);
		
	}
	
	public List<Address> getAllObject(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Address> objList = mapper.scan(Address.class, scanExpression);
		return objList;
	}
	
		
	public Address getObjectById(String parentId, String id){
		Condition hashKeyCondition = new Condition().withComparisonOperator(
				ComparisonOperator.EQ.toString()).withAttributeValueList(
				new AttributeValue().withS(id));
		
		Address obj = new Address();
		
		obj.setEmployeeEntId(parentId);
			DynamoDBQueryExpression<Address> queryExpression = new DynamoDBQueryExpression<Address>()
				.withHashKeyValues(obj)
				.withRangeKeyCondition("EntId", hashKeyCondition);
				
		List<Address> objList = mapper.query(Address.class, queryExpression);
		if(objList!=null && objList.size()>0)
			return objList.get(0);
		else 
			return null;
	}
			public List<Address> findAddressByEmployeeId(String parentId){
		Address obj = new Address();
		obj.setEmployeeEntId(parentId);
		DynamoDBQueryExpression<Address> queryExpression = new DynamoDBQueryExpression<Address>()
				.withHashKeyValues(obj);
		
		List<Address> objList = mapper.query(Address.class, queryExpression);
		return objList;
	}
		

	

}
	