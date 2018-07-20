
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
public class LocationLog {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	// Generated code for attribute: patient
	@Index
	private long patientId;
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long param) {
		this.patientId = param;
	}
			 
	private double locLat;
	       
	public double getLocLat() {
		return locLat;
	}
	public void setLocLat(double param) {
		this.locLat = param;
	}
	 
	private double locLong;
	       
	public double getLocLong() {
		return locLong;
	}
	public void setLocLong(double param) {
		this.locLong = param;
	}
	
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class) 
	private Date logDate;
	       
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date param) {
		this.logDate = param;
	}
	
}
	