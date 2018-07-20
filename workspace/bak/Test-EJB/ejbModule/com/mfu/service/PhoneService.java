
	package com.mfu.service;

	import com.mfu.entity.*;
	import java.util.List;

	public interface PhoneService {

		// Genereated default methods for CRUD
		public void create(Phone param);
	
		public void update(Phone param);
	
		public void delete(long id);

		public List<Phone> getAllPhone();

		public Phone findPhoneById(long id); 

		
		// Generated code for searching attribute: number
		public List<Phone> findPhoneByNumber(String param);
	   
		// Generated code for searching by parent relationship:
		public List<Phone> findPhoneByEmployeeId(long id);
	   
	}
	