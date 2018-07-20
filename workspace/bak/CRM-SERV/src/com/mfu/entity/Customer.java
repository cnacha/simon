
package com.mfu.entity;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Customer")
public class Customer {

	private String entId;
	
	@DynamoDBHashKey(attributeName = "EntId")
		@DynamoDBAutoGeneratedKey
	public String getEntId() {
		return entId;
	}
	public void setEntId(String entId) {
		this.entId = entId;
	}
		
	// Generated code for attribute: name
	private String name;
	@DynamoDBAttribute(attributeName = "Name")
	public String getName() {
		return name;
	}
	public void setName(String param) {
		this.name = param;
	}
		
	// Generated code for attribute: phone
	private String phone;
	@DynamoDBAttribute(attributeName = "Phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String param) {
		this.phone = param;
	}
		
	// Generated code for attribute: address
	private String address;
	@DynamoDBAttribute(attributeName = "Address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String param) {
		this.address = param;
	}
		
	// Generated code for attribute: photo
	private String photo;
	@DynamoDBAttribute(attributeName = "Photo")
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String param) {
		this.photo = param;
	}
		
	private Object photoData;
	public Object getPhotoData() {
		return photoData;
	}
	public void setPhotoData(Object param) {
		this.photoData = param;
	}
		
	// Generated code for attribute: saleOrder
}
	