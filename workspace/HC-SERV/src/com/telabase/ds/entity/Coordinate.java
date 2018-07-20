
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


@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinate {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private HashMap<String, Double> props;
	public double geValue(String property){
		return props.get(property);
	}
	
	public void addValue(String s, Double d){
		props.put(s, d);
	}
	public HashMap<String, Double> getProps() {
		return this.props;
	}
	public void setProps(HashMap<String, Double> props) {
		this.props = props;
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
	