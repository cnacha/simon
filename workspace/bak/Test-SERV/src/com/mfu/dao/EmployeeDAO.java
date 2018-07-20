
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

public class EmployeeDAO {
	
	private static final String tableName = "Employee";
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
	
	public void create(Employee obj){
		mapper.save(obj);
	}
	
	public void update(Employee obj){
		mapper.save(obj);
	}
	
	public void delete(String id){
		Employee obj = new Employee();
		obj.setEntId(id);

		
		//delete related address
		AddressDAO addressDAO = new AddressDAO();
		try {
			addressDAO.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Address> addressList = addressDAO.findAddressByEmployeeId(id);
		for(Address cobj: addressList){
			mapper.delete(cobj);
		}		
			
		//delete related phone
		PhoneDAO phoneDAO = new PhoneDAO();
		try {
			phoneDAO.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Phone> phoneList = phoneDAO.findPhoneByEmployeeId(id);
		for(Phone cobj: phoneList){
			mapper.delete(cobj);
		}		
			
		mapper.delete(obj);
		
	}
	
	public List<Employee> getAllObject(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Employee> objList = mapper.scan(Employee.class, scanExpression);
		return objList;
	}
	
		
	public Employee getObjectById(String id){
		Employee obj = mapper.load(Employee.class, id);
		return obj;
	}
			

	

}
	