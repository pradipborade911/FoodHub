package com.foodhub.service;

import java.util.List;

import com.foodhub.dto.UserProfile;
import jakarta.transaction.Transactional;

@Transactional
public interface VendorService {

	public List<UserProfile> getAllVendorsList();

	public UserProfile getVendorById(Long vendorId);

	public UserProfile updateVendor(UserProfile detachedVendor);

	public String deleteVendorById(Long vendorId);

	public String approveVendorById(Long vendorId);

	public List<UserProfile> getAllUnapprovedVendors();

	/*
	 * public UserProfile getUnapprovedVendorById(Long vendorId);
	 */	
	public String changeAvailability(Long id);

	public String changeBlockingStatus(Long id);

	public List<UserProfile> getCustomersByVendorId(Long vendorId);

	public List<UserProfile> getAllApprovedVendors();
	
	public List<UserProfile> findAllVendorsByPincode(Integer pin);

	UserProfile getVendorByEmail(String email);

}
