
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
	public class Employee implements Serializable{
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long entId;
		public long getId() {
			return this.entId;
		}
		public void setId(long entId) {
			this.entId = entId;
		}

		
	// Generated code for attribute: firstname
		private String firstname;
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String param) {
			this.firstname = param;
		}
	
	// Generated code for attribute: lastname
		private String lastname;
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String param) {
			this.lastname = param;
		}
	
	// Generated code for attribute: birthday
		private Date birthday;
		public Date getBirthday() {
			return birthday;
		}
		public void setBirthday(Date param) {
			this.birthday = param;
		}
	
	// Generated code for attribute: photo
		private String photo;
		public String getPhoto() {
			return photo;
		}
		public void setPhoto(String param) {
			this.photo = param;
		}
	
		@Transient
		private Object photoData;
		public Object getPhotoData() {
			return photoData;
		}
		public void setPhotoData(Object param) {
			this.photoData = param;
		}
	
	// Generated code for attribute: salary
		private int salary;
		public int getSalary() {
			return salary;
		}
		public void setSalary(int param) {
			this.salary = param;
		}
	
	// Generated code for attribute: position
		private String position;
		public String getPosition() {
			return position;
		}
		public void setPosition(String param) {
			this.position = param;
		}
	
	// Generated code for attribute: address
		@OneToOne(mappedBy="employee", cascade={CascadeType.ALL})
		private Address address;
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address param) {
			this.address = param;
		}
	
	// Generated code for attribute: phone
		@OneToMany(mappedBy="employee", cascade={CascadeType.ALL})
		private Set<Phone> phone;
		public Set<Phone> getPhone() {
			return phone;
		}
		public void setPhone(Set<Phone> param) {
			this.phone = param;
		}
	
	}
	