
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
public class Phone {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	// Generated code for attribute: label
	private String label;
	public String getLabel() {
		return label;
	}
	public void setLabel(String param) {
		this.label = param;
	}
	
	// Generated code for attribute: number@Index
	private String number;
	public String getNumber() {
		return number;
	}
	public void setNumber(String param) {
		this.number = param;
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
	