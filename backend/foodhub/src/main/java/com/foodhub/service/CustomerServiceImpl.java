package com.foodhub.service;

import java.util.ArrayList;
import java.util.List;

import com.foodhub.dto.SignUpRequest;
import com.foodhub.dto.CustomerDTO;
import com.foodhub.repository.ImageService;
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

	@Autowired
	ImageService imageService;

	@Override
	public List<CustomerDTO> getAllCustomers() {

		List<CustomerDTO> allCustomersList = new ArrayList<CustomerDTO>();

		addressRepo.findAllByAddressTypeAndVendor(AddressType.HOME, null)
				.orElseThrow(() -> new ResourceNotFoundException("Error occured while fecthing addresses!"))
				.forEach(address -> { // Get Vendor corresponding to an address
										// Create addresss Dto and put it in the list
					CustomerDTO customerDto = modelMapper.map(address.getCustomer(), CustomerDTO.class);
					customerDto.setAddress(address.toString());
					customerDto.setProfilePic(imageService.getImageFile(address.getCustomer().getProfilePicPath()));
					allCustomersList.add(customerDto);
				});
		if (allCustomersList.isEmpty())
			throw new ResourceNotFoundException("No customers exists");
		return allCustomersList;
	}

	@Override
	public CustomerDTO getCustomerById(Long customerId) {
		// Find Customer
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Customer Id!")) ;
		// Find Home type address for the customer 
		Address address = addressRepo.findByAddressTypeAndCustomer(AddressType.HOME,customer) ;

		CustomerDTO customerDto = modelMapper.map(customer, CustomerDTO.class) ;
		customerDto.setProfilePic(imageService.getImageFile(customer.getProfilePicPath()));
		customerDto.setAddress(address.toString());
		return customerDto ;
	}

	@Override
	public CustomerDTO getCustomerByEmail(String email) {
		// Find Customer
		Customer customer = customerRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Customer Id!")) ;
		// Find Home type address for the customer 
		Address address = addressRepo.findByAddressTypeAndCustomer(AddressType.HOME,customer) ;

		CustomerDTO customerDto = modelMapper.map(customer, CustomerDTO.class) ;
		customerDto.setProfilePic(imageService.getImageFile(customer.getProfilePicPath()));
		customerDto.setAddress(address.toString());
		return customerDto ;
	}
	
	@Override
	public CustomerDTO updateCustomerDetails(CustomerDTO detachedCustomer) {
		customerRepo.findById(detachedCustomer.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
		Customer customer = customerRepo.save(modelMapper.map(detachedCustomer, Customer.class));
		CustomerDTO customerDto = modelMapper.map(customer, CustomerDTO.class);
		customerDto.setProfilePic(imageService.getImageFile(customer.getProfilePicPath()));
		return customerDto;
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
