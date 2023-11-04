package com.foodhub.service;

import com.foodhub.dto.UserProfile;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foodhub.entities.Address;
import com.foodhub.entities.Customer;
import com.foodhub.entities.Vendor;
import com.foodhub.enums.AddressType;
import com.foodhub.enums.UserRole;
import com.foodhub.execptions.ResourceNotFoundException;
import com.foodhub.repository.AddressRepository;
import com.foodhub.repository.CustomerRepository;
import com.foodhub.repository.VendorRepository;

@Transactional
@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	ModelMapper modelMapper ;

	@Autowired
	AddressRepository addressRepo ;

	@Autowired
	CustomerRepository customerRepo ;

	@Autowired
	VendorRepository vendorRepo  ;

	@Override
	public UserProfile addCustomerAddress(Long id, Address address) {
		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid User Id")) ;

				if(addressRepo.findByAddressTypeAndCustomer(AddressType.HOME, customer) == null)
					address.setAddressType(AddressType.HOME);
				else
					address.setAddressType(AddressType.OTHER);

				address.setCustomer(customer);
				addressRepo.save(address) ;
				UserProfile customerUser = modelMapper.map(customer, UserProfile.class) ;
				customerUser.setAddress(address.toString());
				return customerUser ;
			}

	@Override
	public UserProfile addVendorAddress(Long id, Address address) {
				Vendor vendor = vendorRepo.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("Invalid User Id")) ;
				if(addressRepo.findByAddressTypeAndVendor(AddressType.HOME, vendor) == null)
					address.setAddressType(AddressType.HOME);
				else
					address.setAddressType(AddressType.OTHER);

				address.setVendor(vendor);
				addressRepo.save(address) ;
				UserProfile vendorUser = modelMapper.map(vendor, UserProfile.class) ;
				vendorUser.setAddress(address.toString());
				return vendorUser ;
			}
	}


