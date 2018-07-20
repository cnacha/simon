
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

public class CustomerDAO {
	
	private static final String tableName = "Customer";
	private AWSFacade facade = null;
	private static DynamoDBMapper mapper = null;
	
	public void init() throws Exception{
		facade = new AWSFacade();
		AmazonDynamoDBClient client = facade.connect();
		mapper = new DynamoDBMapper(client);
		if(!facade.isTableAvailable(tableName))
			facade.createTable(tableName, 10L, 5L,  
		
				"EntId", "S");
	}
	
	public void reset() throws Exception{
		facade.deleteTable(tableName);
		init();
	}
	
	public void create(Customer obj){
		mapper.save(obj);
	}
	
	public void update(Customer obj){
		mapper.save(obj);
	}
	
	public void delete(String id){
		Customer obj = new Customer();
		obj.setEntId(id);

		
		//delete related saleOrder
		SaleOrderDAO saleorderDAO = new SaleOrderDAO();
		try {
			saleorderDAO.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<SaleOrder> saleorderList = saleorderDAO.findSaleOrderByCustomerId(id);
		for(SaleOrder cobj: saleorderList){
			mapper.delete(cobj);
		}		
			
		mapper.delete(obj);
		
	}
	
	public List<Customer> getAllObject(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Customer> objList = mapper.scan(Customer.class, scanExpression);
		return objList;
	}
	
		
	public Customer getObjectById(String id){
		Customer obj = mapper.load(Customer.class, id);
		return obj;
	}
			

	

}
	