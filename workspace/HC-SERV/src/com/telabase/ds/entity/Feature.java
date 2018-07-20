
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
public class Feature {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Index 
	private String name;
	       
	public String getName() {
		return name;
	}
	public void setName(String param) {
		this.name = param;
	}
	 
	private String axis;
	       
	public String getAxis() {
		return axis;
	}
	public void setAxis(String param) {
		this.axis = param;
	}
	 
	private String type;
	       
	public String getType() {
		return type;
	}
	public void setType(String param) {
		this.type = param;
	}
	
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class) 
	private Date modifiedDate;
	       
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date param) {
		this.modifiedDate = param;
	}
	
	// Generated code for attribute: target
	@Index
	private long targetId;
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long param) {
		this.targetId = param;
	}
			
}
	