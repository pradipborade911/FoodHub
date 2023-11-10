 package com.foodhub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.foodhub.dto.CustomerDTO;
import com.foodhub.dto.VendorDTO;
import com.foodhub.enums.UserRole;
import com.foodhub.execptions.ResourceNotFoundException;
import com.foodhub.security.UserRepo;
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
	private UserRepo userRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<VendorDTO> getAllVendorsList() {
		
	
		List<VendorDTO> allVendorsList = new ArrayList<VendorDTO>() ;
		
		addressRepo.findAllByAddressTypeAndCustomer(AddressType.HOME, null)
				.orElseThrow(()->new ResourceNotFoundException("Error occured while fecthing addresses!"))
				.forEach(address -> { //Get Vendor corresponding to an address
									// Create addresss Dto and put it in the list
					VendorDTO vendorDTO = modelMapper.map(address.getVendor(), VendorDTO.class) ;
					vendorDTO.setAddress(address.toString()) ;
					allVendorsList.add(vendorDTO);
				});
		
		if (allVendorsList.isEmpty())
				throw new ResourceNotFoundException("No vendors exists" ) ;
		return allVendorsList;
	}

	@Override
	public List<VendorDTO> getAllApprovedVendors() {

		List<VendorDTO> allApprovedVendorsList = new ArrayList<VendorDTO>();

		addressRepo.findAllByAddressTypeAndCustomer(AddressType.HOME, null)
				.orElseThrow(() -> new ResourceNotFoundException("Error occured while fecthing addresses!"))
				.forEach(address -> { //Get Vendor corresponding to an address
					// Create addresss Dto and put it in the list'
					System.out.println(address.getVendor().getFirstName());
					if (address.getVendor().isVerified()) {
						VendorDTO vendorDTO = modelMapper.map(address.getVendor(), VendorDTO.class);
						vendorDTO.setAddress(address.toString());
						allApprovedVendorsList.add(vendorDTO);
					}
				});

		if (allApprovedVendorsList.isEmpty())
			throw new ResourceNotFoundException("No vendors exists");

		return allApprovedVendorsList;
	}


	@Override
	public List<VendorDTO> getAllUnapprovedVendors() {
		List<Vendor> vendors = vendorRepo.findAllByIsVerifiedFalse().orElseThrow(()->new ResourceNotFoundException("Error occured while fecthing vendors!"));
		List<VendorDTO> vendorsDTO = vendors
				.stream()
				.map(vendor -> {userRepo.findUsernameById(vendor.getUser().getId()); return modelMapper.map(vendor, VendorDTO.class);})
				.map(vendor -> {vendor.setUserRole(UserRole.VENDOR); return vendor;})
				.toList();

		if (vendorsDTO.isEmpty())
			throw new ResourceNotFoundException("No vendors exists" ) ;
		
		return vendorsDTO ;
	}

	
	@Override
	public VendorDTO getVendorById(Long vendorId) {
		// Find Vendor
		Vendor vendor = vendorRepo.findById(vendorId)
				.orElseThrow(()-> new ResourceNotFoundException("Invalid vendor ID"));
		// Find Home type address for the vendor
		Address address = addressRepo.findByAddressTypeAndVendor(AddressType.HOME,vendor) ;
		VendorDTO vendorDTO =  modelMapper.map(vendor, VendorDTO.class);
		vendorDTO.setAddress(address.toString());
		return vendorDTO;
	}

	@Override
	public VendorDTO getVendorByEmail(String email) {
		// Find Vendor
		Vendor vendor = vendorRepo.findByEmail(email)
				.orElseThrow(()-> new ResourceNotFoundException("Invalid vendor ID"));
		
		System.out.println("In VendorService "+ vendor.getFirstName());
		// Find Home type address for the vendor
		Address address = addressRepo.findByAddressTypeAndVendor(AddressType.HOME,vendor) ;
		System.out.println("Got Address "+ address.getCity());
		
		VendorDTO vendorDTO =  modelMapper.map(vendor, VendorDTO.class);
		vendorDTO.setAddress(address.toString());
		return vendorDTO;
	}
	
	@Override
	public VendorDTO updateVendor(VendorDTO detachedVendor) {
		Vendor vendor = modelMapper.map(detachedVendor, Vendor.class);
		return modelMapper.map(vendorRepo.save(vendor), VendorDTO.class);
	}

	@Override
	public String deleteVendorById(Long vendorId) {
		// Delete all Addresses
		addressRepo.deleteAllByVendorId(vendorId);
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
	public List<CustomerDTO> getCustomersByVendorId(Long vendorId) {
		Vendor vendor  = vendorRepo.findById(vendorId).
				orElseThrow(()-> new ResourceNotFoundException("Invalid vendor ID"));
		
		List<Tiffin> tiffinList = tiffinRepo.findAllByVendorId(vendor.getId())
				.orElseThrow( () -> new ResourceNotFoundException("This vendor has no tiffins.") );

		List<CustomerOrder> customerOrderListForVendor = new ArrayList<>() ;
		List<Customer> customerListForVendor  = new ArrayList<>() ; 
		
		tiffinList.forEach(tiffin-> {
				customerOrderListForVendor.addAll(customerOrderRepo.findByTiffinId(tiffin.getId()));
				});
				
		customerOrderListForVendor.forEach(customerOrder-> {
				customerListForVendor.add(customerOrder.getCustomer());
			});
		
		List<CustomerDTO> distinctCustomers = customerListForVendor.stream()
                				.distinct()
                				.map((customer)->{
									CustomerDTO customerDto = modelMapper.map(customer, CustomerDTO.class);
        							Address homeAddress = addressRepo.findByCustomerIdAndAddressType(customerDto.getId(), AddressType.HOME)
                							.orElseThrow(()->new ResourceNotFoundException("Error occured while fecting customer's HOME address"));
                					customerDto.setAddress(homeAddress.toString());
                					return customerDto;
                				})
                				.collect(Collectors.toList());
		
		return distinctCustomers;

	}
	
	public List<VendorDTO> findAllVendorsByPincode(Integer pincode){
		List<Vendor> list = addressRepo.findAllVendorsByPincode(pincode)
				.orElseThrow(()->new ResourceNotFoundException("Error occured while fetching vendors"));
				
		return list.stream()
				.map((vendor)->{
					VendorDTO vendorDTO = modelMapper.map(vendor, VendorDTO.class);
					
					Address homeAddress = addressRepo.findByVendorIdAndAddressType(vendorDTO.getId(), AddressType.HOME)
							.orElseThrow(()->new ResourceNotFoundException("Error occured while fecting vendor's HOME address"));
					
					vendorDTO.setAddress(homeAddress.toString());
					return vendorDTO;
				})
				.collect(Collectors.toList());
	}
	
}


