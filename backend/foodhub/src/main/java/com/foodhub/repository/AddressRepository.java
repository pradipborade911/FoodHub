package com.foodhub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.foodhub.entities.Address;
import com.foodhub.entities.Customer;
import com.foodhub.entities.Vendor;
import com.foodhub.enums.AddressType;

public interface AddressRepository extends JpaRepository<Address, Long> {

	void deleteAllByVendorId(Long id);

	Optional<List<Address>> findAllByCustomerId(Long customerId);
	
	Optional<List<Address>> findAllByAddressType(AddressType addressType);

	Optional<List<Address>> findAllByAddressTypeAndVendor(AddressType addressType, Vendor vendor);

	Optional<List<Address>> findAllByAddressTypeAndCustomer(AddressType addressType, Customer customer);
	
	Optional<List<Address>> findAllByVendorId(Long vendorId);

	Address findByAddressTypeAndVendor(AddressType home, Vendor vendor);
	
	Optional<List<Address>> findAllByPincode(Integer pincode);
	
	@Query("SELECT vendor FROM Address WHERE pincode=?1 ")
	Optional<List<Vendor>> findAllVendorsByPincode(Integer pincode);

	Optional<Address> findByCustomerIdAndAddressType(Long id, AddressType addressType);
	
	Optional<Address> findByVendorIdAndAddressType(Long id, AddressType addressType);

	Address findByAddressTypeAndCustomer(AddressType home, Customer customer);

}
