
	package com.mfu.service;

	import com.mfu.entity.*;
	import java.util.List;

	public interface AddressService {

		// Genereated default methods for CRUD
		public void create(Address param);
	
		public void update(Address param);
	
		public void delete(long id);

		public List<Address> getAllAddress();

		public Address findAddressById(long id); 

		
		// Generated code for searching by parent relationship:
		public List<Address> findAddressByEmployeeId(long id);
	   
	}
	