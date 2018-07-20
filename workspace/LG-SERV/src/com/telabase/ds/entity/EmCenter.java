
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
public class EmCenter {

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
	 
	private String description;
	       
	public String getDescription() {
		return description;
	}
	public void setDescription(String param) {
		this.description = param;
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
	
}
	