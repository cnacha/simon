
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
	public class Phone implements Serializable{
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long entId;
		public long getId() {
			return this.entId;
		}
		public void setId(long entId) {
			this.entId = entId;
		}

		
	// Generated code for attribute: label
		private String label;
		public String getLabel() {
			return label;
		}
		public void setLabel(String param) {
			this.label = param;
		}
	
	// Generated code for attribute: number
		private String number;
		public String getNumber() {
			return number;
		}
		public void setNumber(String param) {
			this.number = param;
		}
	
	// Generated code for attribute: employee
		@ManyToOne
		private Employee employee;
		public Employee getEmployee() {
			return employee;
		}
		public void setEmployee(Employee param) {
			this.employee = param;
		}
	
	}
	