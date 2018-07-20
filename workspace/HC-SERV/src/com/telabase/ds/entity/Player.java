
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
public class Player {

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
	
	// Generated code for attribute: cluster
	@Index
	private long clusterId;
	public long getClusterId() {
		return clusterId;
	}
	public void setClusterId(long param) {
		this.clusterId = param;
	}
			
}
	