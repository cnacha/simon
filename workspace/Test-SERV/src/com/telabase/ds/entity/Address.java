
package com.telabase.ds.entity;

import java.util.Date;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.telabase.json.formatter.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	// Generated code for attribute: houseNo
	private String houseNo;
	public String getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(String param) {
		this.houseNo = param;
	}
	
	// Generated code for attribute: street
	private String street;
	public String getStreet() {
		return street;
	}
	public void setStreet(String param) {
		this.street = param;
	}
	
	// Generated code for attribute: province
	private String province;
	public String getProvince() {
		return province;
	}
	public void setProvince(String param) {
		this.province = param;
	}
	
	// Generated code for attribute: employee
	@Index
	private long employeeId;
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long param) {
		this.employeeId = param;
	}
	    
}
	