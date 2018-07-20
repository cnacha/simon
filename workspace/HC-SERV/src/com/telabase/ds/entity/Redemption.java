
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
public class Redemption {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	 
	private int amount;
	       
	public int getAmount() {
		return amount;
	}
	public void setAmount(int param) {
		this.amount = param;
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
			
}
	