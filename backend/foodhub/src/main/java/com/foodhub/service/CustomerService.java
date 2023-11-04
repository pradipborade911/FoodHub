package com.foodhub.service;

import com.foodhub.dto.SignUpRequest;
import com.foodhub.dto.UserProfile;

import java.util.List;

public interface CustomerService {

	public List<UserProfile> getAllCustomers();

	public UserProfile getCustomerById(Long customerId);

	public UserProfile updateCustomerDetails(Long id, SignUpRequest customer);

	public String deleteCustomerById(Long customerId);

	public String changeBlockingStatus(Long customerId);
	
	public UserProfile getCustomerByEmail(String Email);
}
