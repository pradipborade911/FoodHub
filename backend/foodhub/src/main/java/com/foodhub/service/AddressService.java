package com.foodhub.service;

import com.foodhub.dto.CustomerDTO;
import com.foodhub.entities.Address;

public interface AddressService {
	public CustomerDTO addCustomerAddress(Long id, Address address);
	public CustomerDTO addVendorAddress(Long id, Address address);

}
