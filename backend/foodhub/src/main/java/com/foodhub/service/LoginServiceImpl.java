package com.foodhub.service;

import com.foodhub.dto.SignInRequest;
import com.foodhub.dto.SignInResponse;
import com.foodhub.dto.SignUpRequest;
import com.foodhub.entities.Address;
import com.foodhub.entities.Customer;
import com.foodhub.entities.Vendor;
import com.foodhub.enums.AddressType;
import com.foodhub.enums.UserRole;
import com.foodhub.execptions.ResourceNotFoundException;
import com.foodhub.repository.AddressRepository;
import com.foodhub.repository.CustomerRepository;
import com.foodhub.repository.VendorRepository;
import com.foodhub.security.JwtUtils;
import com.foodhub.security.User;
import com.foodhub.security.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public String signup(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), UserRole.valueOf(signUpRequest.getUserRole()));
        userRepo.save(user);
        Address address = signUpRequest.getAddress();

        switch (signUpRequest.getUserRole()){
            case "ADMIN":
                //new Object(request.getFirstName(), request.getLastName(), request.getEmail(), request.getMobile(), request.getPassword());
                break;
            case "CUSTOMER":
                Customer customer = new Customer(signUpRequest.getFirstName(),
                        signUpRequest.getLastName(),
                        signUpRequest.getEmail(),
                        signUpRequest.getMobile(),
                        LocalDate.now(),
                        user,
                        false);
                customerRepository.save(customer);
                address.setCustomer(customer);
                break;
            case "VENDOR":
                Vendor vendor = new Vendor(false,
                        false,
                        false,
                        signUpRequest.getFirstName(),
                        signUpRequest.getLastName(),
                        signUpRequest.getEmail(),
                        signUpRequest.getMobile(),
                        LocalDate.now(),
                        user);
                vendorRepository.save(vendor);
                address.setVendor(vendor);
        }
        address.setAddressType(AddressType.HOME);
        addressRepository.save(address);
        return "Created Successfully";
    }

    @Override
    public SignInResponse signin(SignInRequest signInRequest) {
        SignInResponse signInResponse = null;
        UsernamePasswordAuthenticationToken unverifiedAuthToken = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());
        authenticationManager.authenticate(unverifiedAuthToken);
        //successfully verified
        User user = userRepo.findByUsername(signInRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        switch (user.getUserRole().toString()){
            case "ADMIN":
                signInResponse = modelMapper.map(user, SignInResponse.class);
                break;
            case "VENDOR":
                Vendor vendor = vendorRepository.findByUser(user).orElseThrow(() -> new UsernameNotFoundException("Vendor not found "));
                signInResponse = modelMapper.map(vendor, SignInResponse.class);
                signInResponse.setUserRole(UserRole.VENDOR);
                Address vendorAddress = addressRepository.findByVendorIdAndAddressType(vendor.getId(), AddressType.HOME).orElseThrow(() -> new ResourceNotFoundException("Error while fetching address"));
                signInResponse.setAddress(vendorAddress.toString());
                break;
            case "CUSTOMER":
                Customer customer = customerRepository.findByUser(user).orElseThrow(() -> new UsernameNotFoundException("Customer not found "));
                signInResponse = modelMapper.map(customer, SignInResponse.class);
                signInResponse.setUserRole(UserRole.CUSTOMER);
                Address customerAddress = addressRepository.findByVendorIdAndAddressType(customer.getId(), AddressType.HOME).orElseThrow(() -> new ResourceNotFoundException("Error while fetching address"));
                signInResponse.setAddress(customerAddress.toString());
                break;
        }
        String jwtToken = JwtUtils.generateJwtToken(user);
        signInResponse.setJwt(jwtToken);
        return signInResponse;
        
    }
}
