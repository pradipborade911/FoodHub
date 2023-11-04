 package com.foodhub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.foodhub.dto.UserProfile;
import com.foodhub.execptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foodhub.entities.Address;
import com.foodhub.entities.Customer;
import com.foodhub.entities.CustomerOrder;
import com.foodhub.entities.Tiffin;
import com.foodhub.entities.Vendor;
import com.foodhub.enums.AddressType;
import com.foodhub.repository.AddressRepository;
import com.foodhub.repository.CustomerOrderRepository;
import com.foodhub.repository.CustomerRepository;
import com.foodhub.repository.TiffinRepository;
import com.foodhub.repository.VendorRepository;

@Transactional
@Service
public class VendorServiceImpl implements VendorService {
	
	@Autowired
	private VendorRepository vendorRepo;
	@Autowired
	private CustomerRepository customerRepo ;
	@Autowired
	private TiffinRepository tiffinRepo ;
	@Autowired
	private CustomerOrderRepository customerOrderRepo ;
	@Autowired
	private AddressRepository addressRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<UserProfile> getAllVendorsList() {
		
	
		List<UserProfile> allVendorsList = new ArrayList<UserProfile>() ;
		
		addressRepo.findAllByAddressTypeAndCustomer(AddressType.HOME, null)
				.orElseThrow(()->new ResourceNotFoundException("Error occured while fecthing addresses!"))
				.forEach(address -> { //Get Vendor corresponding to an address
									// Create addresss Dto and put it in the list
					UserProfile userProfile = modelMapper.map(address.getVendor(), UserProfile.class) ;
					userProfile.setAddress(address.toString()) ;
					allVendorsList.add(userProfile);
				});
		
		if (allVendorsList.isEmpty())
				throw new ResourceNotFoundException("No vendors exists" ) ;
		return allVendorsList;
	}

	@Override
	public List<UserProfile> getAllApprovedVendors() {

		List<UserProfile> allApprovedVendorsList = new ArrayList<UserProfile>();

		addressRepo.findAllByAddressTypeAndCustomer(AddressType.HOME, null)
				.orElseThrow(() -> new ResourceNotFoundException("Error occured while fecthing addresses!"))
				.forEach(address -> { //Get Vendor corresponding to an address
					// Create addresss Dto and put it in the list'
					System.out.println(address.getVendor().getFirstName());
					if (address.getVendor().isVerified()) {
						UserProfile userProfile = modelMapper.map(address.getVendor(), UserProfile.class);
						userProfile.setAddress(address.toString());
						allApprovedVendorsList.add(userProfile);
					}
				});

		if (allApprovedVendorsList.isEmpty())
			throw new ResourceNotFoundException("No vendors exists");

		return allApprovedVendorsList;
	}


	@Override
	public List<UserProfile> getAllUnapprovedVendors() {
		List<UserProfile> allUnApprovedVendorsList = new ArrayList<UserProfile>() ;
		
		addressRepo.findAllByAddressTypeAndCustomer(AddressType.HOME,null)
				.orElseThrow(()->new ResourceNotFoundException("Error occured while fecthing addresses!"))
				.forEach(address -> { //Get Vendor corresponding to an address
									// Create addresss Dto and put it in the list
					if(!address.getVendor().isVerified()) {
						UserProfile userProfile = modelMapper.map(address.getVendor(), UserProfile.class) ;
						userProfile.setAddress(address.toString()) ;
						allUnApprovedVendorsList.add(userProfile);					
					}
				});		
		if (allUnApprovedVendorsList.isEmpty())
			throw new ResourceNotFoundException("No vendors exists" ) ;
		
		return allUnApprovedVendorsList ;
	}

	
	@Override
	public UserProfile getVendorById(Long vendorId) {
		// Find Vendor
		Vendor vendor = vendorRepo.findById(vendorId)
				.orElseThrow(()-> new ResourceNotFoundException("Invalid vendor ID"));
		
		// Find Home type address for the vendor
		Address address = addressRepo.findByAddressTypeAndVendor(AddressType.HOME,vendor) ;

		
		UserProfile userProfile =  modelMapper.map(vendor, UserProfile.class);
		userProfile.setAddress(address.toString());
		return userProfile;
	}

	@Override
	public UserProfile getVendorByEmail(String email) {
		// Find Vendor
		Vendor vendor = vendorRepo.findByEmail(email)
				.orElseThrow(()-> new ResourceNotFoundException("Invalid vendor ID"));
		
		System.out.println("In VendorService "+ vendor.getFirstName());
		// Find Home type address for the vendor
		Address address = addressRepo.findByAddressTypeAndVendor(AddressType.HOME,vendor) ;
		System.out.println("Got Address "+ address.getCity());
		
		UserProfile userProfile =  modelMapper.map(vendor, UserProfile.class);
		userProfile.setAddress(address.toString());
		return userProfile;
	}
	
	@Override
	public UserProfile updateVendor(UserProfile detachedVendor) {
		Vendor vendor = modelMapper.map(detachedVendor, Vendor.class);
		return modelMapper.map(vendorRepo.save(vendor), UserProfile.class);
	}

	@Override
	public String deleteVendorById(Long vendorId) {
		// Get all addresses corresponding to the vendor
		List<Address> vendorAddressList = addressRepo.findAllByVendorId(vendorId)
				.orElseThrow(()->new ResourceNotFoundException("Error occursed while fecthing vendor's addresses"));
		
		// Delete all Addresses 
		for (Address address : vendorAddressList) {
			addressRepo.delete(address);
		}
		
		// Delete Vendor
		Vendor vendor = vendorRepo.findById(vendorId)
						.orElseThrow(()-> new ResourceNotFoundException("Invalid vendor ID"));
		
		vendorRepo.delete(vendor);
		
		return "Vendor " + vendor.getFirstName() + " " + vendor.getLastName() + " has been removed!";
	}


	@Override
	public String approveVendorById(Long vendorId) {
		Vendor vendor = vendorRepo.findById(vendorId)
						.orElseThrow(()-> new ResourceNotFoundException("Invalid vendor ID"));
		vendor.setVerified(true) ;
		vendorRepo.save(vendor);
		return "Vendor " + vendor.getFirstName() + " " + vendor.getLastName() + " has been approved!";
	}


	@Override
	public String changeAvailability(Long id) {
		Vendor vendor = vendorRepo.findById(id)
					.orElseThrow(() ->  new ResourceNotFoundException("No UnApproved Vendors!")) ;
		vendor.setAvailable(!vendor.isAvailable());
		String status = vendor.isAvailable()?"Available":"UnAvailable" ;
		return "Vendor " + vendor.getFirstName() + " "+ vendor.getLastName()+ " is been " + status ;
	}

	@Override
	public String changeBlockingStatus(Long id) {
		Vendor vendor = vendorRepo.findById(id)
					.orElseThrow(() ->  new ResourceNotFoundException("No UnApproved Vendors!"));
		vendor.setBlocked(!vendor.isBlocked());
		String status = vendor.isBlocked()?"Blocked":"Unblocked" ;
		return "Vendor " + vendor.getFirstName() + " "+ vendor.getLastName()+ " has been " + status ;
	}

	@Override
	public List<UserProfile> getCustomersByVendorId(Long vendorId) {
		Vendor vendor  = vendorRepo.findById(vendorId).
				orElseThrow(()-> new ResourceNotFoundException("Invalid vendor ID"));		
//				.stream().map(customer->modelmapper.map(customer, CustomerDto.class))
//				.collect(Collectors.toList());
		
		List<Tiffin> tiffinList = tiffinRepo.findAllByVendorId(vendor.getId())
				.orElseThrow( () -> new ResourceNotFoundException("This vendor has no tiffins.") );

		List<CustomerOrder> customerOrderListForVendor = new ArrayList<>() ;
		//List<Customer> customerListForVendor = new ArrayList<>() ;
		List<Customer> customerListForVendor  = new ArrayList<>() ; 
		
		tiffinList.forEach(tiffin-> {
				customerOrderListForVendor.addAll(customerOrderRepo.findByTiffinId(tiffin.getId()));
				});
				
		customerOrderListForVendor.forEach(customerOrder-> {
				customerListForVendor.add(customerOrder.getCustomer());
			});
		
		List<UserProfile> distinctCustomers = customerListForVendor.stream()
                				.distinct()
                				.map((customer)->{
									UserProfile customerDto = modelMapper.map(customer, UserProfile.class);
        							Address homeAddress = addressRepo.findByCustomerIdAndAddressType(customerDto.getId(), AddressType.HOME)
                							.orElseThrow(()->new ResourceNotFoundException("Error occured while fecting customer's HOME address"));
                					customerDto.setAddress(homeAddress.toString());
                					return customerDto;
                				})
                				.collect(Collectors.toList());
		
		return distinctCustomers;
		
//		return distinctCustomers.stream()
//				   .map( customer -> modelMapper.map(customer, CustomerDto.class))
//				   .collect(Collectors.toList());
		
		
	}
	
	public List<UserProfile> findAllVendorsByPincode(Integer pincode){
		List<Vendor> list = addressRepo.findAllVendorsByPincode(pincode)
				.orElseThrow(()->new ResourceNotFoundException("Error occured while fetching vendors"));
				
		return list.stream()
				.map((vendor)->{
					UserProfile userProfile = modelMapper.map(vendor, UserProfile.class);
					
					Address homeAddress = addressRepo.findByVendorIdAndAddressType(userProfile.getId(), AddressType.HOME)
							.orElseThrow(()->new ResourceNotFoundException("Error occured while fecting vendor's HOME address"));
					
					userProfile.setAddress(homeAddress.toString());
					return userProfile;
				})
				.collect(Collectors.toList());
	}
	
}


