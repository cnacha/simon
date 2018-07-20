
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
public class Status {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	 
	private double progress;
	       
	public double getProgress() {
		return progress;
	}
	public void setProgress(double param) {
		this.progress = param;
	}
	
	@Index 
	private int cluster;
	       
	public int getCluster() {
		return cluster;
	}
	public void setCluster(int param) {
		this.cluster = param;
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
	
	// Generated code for attribute: player
	@Index
	private long playerId;
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long param) {
		this.playerId = param;
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
	