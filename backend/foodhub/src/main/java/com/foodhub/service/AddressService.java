package com.foodhub.service;

import com.foodhub.dto.UserProfile;
import com.foodhub.entities.Address;

public interface AddressService {
	public UserProfile addCustomerAddress(Long id, Address address);
	public UserProfile addVendorAddress(Long id, Address address);

}
