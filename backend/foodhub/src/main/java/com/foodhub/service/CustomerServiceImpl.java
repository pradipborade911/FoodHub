package com.foodhub.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.foodhub.dto.SignUpRequest;
import com.foodhub.dto.UserProfile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodhub.entities.Address;
import com.foodhub.entities.Customer;
import com.foodhub.enums.AddressType;
import com.foodhub.execptions.ResourceNotFoundException;
import com.foodhub.repository.AddressRepository;
import com.foodhub.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AddressRepository addressRepo;

	@Override
	public List<UserProfile> getAllCustomers() {

		List<UserProfile> allCustomersList = new ArrayList<UserProfile>();

		addressRepo.findAllByAddressTypeAndVendor(AddressType.HOME, null)
				.orElseThrow(() -> new ResourceNotFoundException("Error occured while fecthing addresses!"))
				.forEach(address -> { // Get Vendor corresponding to an address
										// Create addresss Dto and put it in the list
					UserProfile customerDto = modelMapper.map(address.getCustomer(), UserProfile.class);
					customerDto.setAddress(address.toString());
					allCustomersList.add(customerDto);
				});
		if (allCustomersList.isEmpty())
			throw new ResourceNotFoundException("No customers exists");
		return allCustomersList;
	}

	@Override
	public UserProfile getCustomerById(Long customerId) {
		// Find Customer
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Customer Id!")) ;
		// Find Home type address for the customer 
		Address address = addressRepo.findByAddressTypeAndCustomer(AddressType.HOME,customer) ;

		UserProfile customerDto = modelMapper.map(customer, UserProfile.class) ;
		customerDto.setAddress(address.toString());
		return customerDto ;
	}

	@Override
	public UserProfile getCustomerByEmail(String email) {
		// Find Customer
		Customer customer = customerRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Customer Id!")) ;
		// Find Home type address for the customer 
		Address address = addressRepo.findByAddressTypeAndCustomer(AddressType.HOME,customer) ;

		UserProfile customerDto = modelMapper.map(customer, UserProfile.class) ;
		customerDto.setAddress(address.toString());
		return customerDto ;
	}
	
	@Override
	public UserProfile updateCustomerDetails(Long id, SignUpRequest detachedCustomer) {
		customerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
		Customer customer = customerRepo.save(modelMapper.map(detachedCustomer, Customer.class));
		UserProfile customerProfile = modelMapper.map(customer, UserProfile.class);
		return customerProfile;
	}

	@Override
	public String deleteCustomerById(Long customerId) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
		customerRepo.delete(customer);
		return "Customer " + customer.getFirstName() + " " + customer.getLastName() + " has been removed!";
	}

	@Override
	public String changeBlockingStatus(Long customerId) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
		customer.setBlocked(!customer.isBlocked());
		customerRepo.save(customer);
		String status = customer.isBlocked() ? "Blocked" : "Unblocked";
		return "Customer " + customer.getFirstName() + " " + customer.getLastName() + " has been " + status;
	}



//	@Override
//	public CustomerDto changeBlockingStatus(CustomerDto customer) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
