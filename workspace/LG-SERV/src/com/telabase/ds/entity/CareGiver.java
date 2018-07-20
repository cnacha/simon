
package com.telabase.ds.entity;

import java.util.*;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.telabase.json.formatter.*;
import com.telabase.ds.dao.*;


@Entity

@JsonIgnoreProperties(ignoreUnknown = true)
public class CareGiver {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Index 
	private String firstname;
	       
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String param) {
		this.firstname = param;
	}
	 
	private String lastname;
	       
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String param) {
		this.lastname = param;
	}
	 
	private String address;
	       
	public String getAddress() {
		return address;
	}
	public void setAddress(String param) {
		this.address = param;
	}
	 
	private String phone;
	       
	public String getPhone() {
		return phone;
	}
	public void setPhone(String param) {
		this.phone = param;
	}
	 
	private String email;
	       
	public String getEmail() {
		return email;
	}
	public void setEmail(String param) {
		this.email = param;
	}
	
}
	