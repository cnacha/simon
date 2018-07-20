
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
public class DistanceAlert {

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
			 
	private String status;
	       
	public String getStatus() {
		return status;
	}
	public void setStatus(String param) {
		this.status = param;
	}
	 
	private double distance;
	       
	public double getDistance() {
		return distance;
	}
	public void setDistance(double param) {
		this.distance = param;
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
	