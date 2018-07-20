
	package com.mfu.entity;
	import java.io.Serializable;
	import java.util.Date;
	import java.util.Set;

	import javax.persistence.CascadeType;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.OneToMany;
	import javax.persistence.OneToOne;
	import javax.persistence.ManyToOne;
	import javax.persistence.Transient;

	@Entity
	public class Address implements Serializable{
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long entId;
		public long getId() {
			return this.entId;
		}
		public void setId(long entId) {
			this.entId = entId;
		}

		
	// Generated code for attribute: houseNo
		private String houseNo;
		public String getHouseNo() {
			return houseNo;
		}
		public void setHouseNo(String param) {
			this.houseNo = param;
		}
	
	// Generated code for attribute: street
		private String street;
		public String getStreet() {
			return street;
		}
		public void setStreet(String param) {
			this.street = param;
		}
	
	// Generated code for attribute: province
		private String province;
		public String getProvince() {
			return province;
		}
		public void setProvince(String param) {
			this.province = param;
		}
	
	// Generated code for attribute: employee
		@OneToOne
		private Employee employee;
		public Employee getEmployee() {
			return employee;
		}
		public void setEmployee(Employee param) {
			this.employee = param;
		}
	
	}
	