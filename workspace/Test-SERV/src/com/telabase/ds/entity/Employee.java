
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
public class Employee {

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
	
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonSerialize(using = DateSerializer.class) 
	private Date birthday;
	       
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date param) {
		this.birthday = param;
	}
	
}
	