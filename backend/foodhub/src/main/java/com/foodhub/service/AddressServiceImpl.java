package com.foodhub.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foodhub.dto.AddressDto;
import com.foodhub.dto.CustomerDto;
import com.foodhub.dto.VendorDto;
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
	public Object addAddress(AddressDto addressDto) {
			Address address = modelMapper.map(addressDto, Address.class) ;
			if(addressDto.getRole() == UserRole.CUSTOMER) {
				Customer customer = customerRepo.findById(addressDto.getId())
								.orElseThrow(() -> new ResourceNotFoundException("Invalid User Id")) ;

				if(addressRepo.findByAddressTypeAndCustomer(AddressType.HOME, customer) == null)
					address.setAddressType(AddressType.HOME);
				else
					address.setAddressType(AddressType.OTHER);

				address.setCustomer(customer);
				addressRepo.save(address) ;
				CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class) ;
				customerDto.setAddress(addressDto);
				return customerDto ;
			}else {
				Vendor vendor = vendorRepo.findById(addressDto.getId())
								.orElseThrow(() -> new ResourceNotFoundException("Invalid User Id")) ;
				if(addressRepo.findByAddressTypeAndVendor(AddressType.HOME, vendor) == null)
					address.setAddressType(AddressType.HOME);
				else
					address.setAddressType(AddressType.OTHER);

				address.setVendor(vendor);
				addressRepo.save(address) ;
				VendorDto vendorDto = modelMapper.map(vendor, VendorDto.class) ;
				vendorDto.setAddress(addressDto);
				return vendorDto ;
			}
	}

}
