package com.foodhub.service;

import com.foodhub.dto.SignUpRequest;
import com.foodhub.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

	public List<CustomerDTO> getAllCustomers();

	public CustomerDTO getCustomerById(Long customerId);

	public CustomerDTO updateCustomerDetails(CustomerDTO customer);

	public String deleteCustomerById(Long customerId);

	public String changeBlockingStatus(Long customerId);
	
	public CustomerDTO getCustomerByEmail(String Email);
}
