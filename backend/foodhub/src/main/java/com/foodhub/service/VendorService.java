package com.foodhub.service;

import java.util.List;

import com.foodhub.dto.CustomerDTO;
import com.foodhub.dto.VendorDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface VendorService {

	public List<VendorDTO> getAllVendorsList();

	public VendorDTO getVendorById(Long vendorId);

	public VendorDTO updateVendor(VendorDTO detachedVendor);

	public String deleteVendorById(Long vendorId);

	public String approveVendorById(Long vendorId);

	public List<VendorDTO> getAllUnapprovedVendors();

	/*
	 * public UserProfile getUnapprovedVendorById(Long vendorId);
	 */	
	public String changeAvailability(Long id);

	public String changeBlockingStatus(Long id);

	public List<CustomerDTO> getCustomersByVendorId(Long vendorId);

	public List<VendorDTO> getAllApprovedVendors();
	
	public List<VendorDTO> findAllVendorsByPincode(Integer pin);

	VendorDTO getVendorByEmail(String email);

}
