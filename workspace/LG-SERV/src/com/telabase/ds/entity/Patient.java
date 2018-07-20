
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
public class Patient {

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
	
	private String photoFilePath;
	public String getPhotoFilePath() {
		return photoFilePath;
	}
	public void setPhotoFilePath(String param) {
		this.photoFilePath = param;
	}
	 
	private String address;
	       
	public String getAddress() {
		return address;
	}
	public void setAddress(String param) {
		this.address = param;
	}
	 
	private String disease;
	       
	public String getDisease() {
		return disease;
	}
	public void setDisease(String param) {
		this.disease = param;
	}
	 
	private double homeLat;
	       
	public double getHomeLat() {
		return homeLat;
	}
	public void setHomeLat(double param) {
		this.homeLat = param;
	}
	 
	private double homeLong;
	       
	public double getHomeLong() {
		return homeLong;
	}
	public void setHomeLong(double param) {
		this.homeLong = param;
	}
	
}
	