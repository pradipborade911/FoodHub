package com.foodhub.service;

import java.util.List;

import com.foodhub.dto.CustomerDto;

public interface CustomerService {

	public CustomerDto createCustomer(CustomerDto customer);

	public List<CustomerDto> getAllCustomers();

	public CustomerDto getCustomerById(Long customerId);

	public CustomerDto updateCustomerDetails(CustomerDto customer);

	public String deleteCustomerById(Long customerId);

	public String changeBlockingStatus(Long customerId);
	
	public CustomerDto getCustomerByEmail(String Email);
}
