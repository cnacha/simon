
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

public class PhoneDAO {
	
	private static final String tableName = "Phone";
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
	
	public void create(Phone obj){
		mapper.save(obj);
	}
	
	public void update(Phone obj){
		mapper.save(obj);
	}
	
	public void delete(String parentId, String id){
		Phone obj = new Phone();
		obj.setEntId(id);

		
		obj.setEmployeeEntId(parentId);
			
		mapper.delete(obj);
		
	}
	
	public List<Phone> getAllObject(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Phone> objList = mapper.scan(Phone.class, scanExpression);
		return objList;
	}
	
		
	public Phone getObjectById(String parentId, String id){
		Condition hashKeyCondition = new Condition().withComparisonOperator(
				ComparisonOperator.EQ.toString()).withAttributeValueList(
				new AttributeValue().withS(id));
		
		Phone obj = new Phone();
		
		obj.setEmployeeEntId(parentId);
			DynamoDBQueryExpression<Phone> queryExpression = new DynamoDBQueryExpression<Phone>()
				.withHashKeyValues(obj)
				.withRangeKeyCondition("EntId", hashKeyCondition);
				
		List<Phone> objList = mapper.query(Phone.class, queryExpression);
		if(objList!=null && objList.size()>0)
			return objList.get(0);
		else 
			return null;
	}
			public List<Phone> findPhoneByEmployeeId(String parentId){
		Phone obj = new Phone();
		obj.setEmployeeEntId(parentId);
		DynamoDBQueryExpression<Phone> queryExpression = new DynamoDBQueryExpression<Phone>()
				.withHashKeyValues(obj);
		
		List<Phone> objList = mapper.query(Phone.class, queryExpression);
		return objList;
	}
		

	

}
	