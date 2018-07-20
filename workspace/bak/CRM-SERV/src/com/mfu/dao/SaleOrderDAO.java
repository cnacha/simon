
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

public class SaleOrderDAO {
	
	private static final String tableName = "SaleOrder";
	private AWSFacade facade = null;
	private static DynamoDBMapper mapper = null;
	
	public void init() throws Exception{
		facade = new AWSFacade();
		AmazonDynamoDBClient client = facade.connect();
		mapper = new DynamoDBMapper(client);
		if(!facade.isTableAvailable(tableName))
			facade.createTable(tableName, 10L, 5L,  
		
				"CustomerEntId", "S", 
			
				"EntId", "S");
	}
	
	public void reset() throws Exception{
		facade.deleteTable(tableName);
		init();
	}
	
	public void create(SaleOrder obj){
		mapper.save(obj);
	}
	
	public void update(SaleOrder obj){
		mapper.save(obj);
	}
	
	public void delete(String parentId, String id){
		SaleOrder obj = new SaleOrder();
		obj.setEntId(id);

		
		obj.setCustomerEntId(parentId);
			
		mapper.delete(obj);
		
	}
	
	public List<SaleOrder> getAllObject(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<SaleOrder> objList = mapper.scan(SaleOrder.class, scanExpression);
		return objList;
	}
	
		
	public SaleOrder getObjectById(String parentId, String id){
		Condition hashKeyCondition = new Condition().withComparisonOperator(
				ComparisonOperator.EQ.toString()).withAttributeValueList(
				new AttributeValue().withS(id));
		
		SaleOrder obj = new SaleOrder();
		
		obj.setCustomerEntId(parentId);
			DynamoDBQueryExpression<SaleOrder> queryExpression = new DynamoDBQueryExpression<SaleOrder>()
				.withHashKeyValues(obj)
				.withRangeKeyCondition("EntId", hashKeyCondition);
				
		List<SaleOrder> objList = mapper.query(SaleOrder.class, queryExpression);
		if(objList!=null && objList.size()>0)
			return objList.get(0);
		else 
			return null;
	}
			public List<SaleOrder> findSaleOrderByCustomerId(String parentId){
		SaleOrder obj = new SaleOrder();
		obj.setCustomerEntId(parentId);
		DynamoDBQueryExpression<SaleOrder> queryExpression = new DynamoDBQueryExpression<SaleOrder>()
				.withHashKeyValues(obj);
		
		List<SaleOrder> objList = mapper.query(SaleOrder.class, queryExpression);
		return objList;
	}
		

	

}
	