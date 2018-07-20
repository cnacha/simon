
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
public class EmRequest {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class) 
	private Date submitDate;
	       
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date param) {
		this.submitDate = param;
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
			
	// Generated code for attribute: emCenter
	@Index
	private long emCenterId;
	public long getEmCenterId() {
		return emCenterId;
	}
	public void setEmCenterId(long param) {
		this.emCenterId = param;
	}
			
}
	