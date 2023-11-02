package com.foodhub.service;

import com.foodhub.dto.SignInRequest;
import com.foodhub.dto.SignInResponse;
import com.foodhub.dto.SignUpRequest;
import com.foodhub.entities.Customer;
import com.foodhub.entities.Vendor;
import com.foodhub.enums.UserRole;
import com.foodhub.repository.VendorRepository;
import com.foodhub.security.JwtUtils;
import com.foodhub.security.User;
import com.foodhub.security.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    VendorRepository vendorRepository;

    @Override
    public String signup(SignUpRequest request) {
        User newUser = new User(request.getEmail(), request.getPassword(), UserRole.valueOf(request.getUserRole()));
        userRepo.save(newUser);
        switch (request.getUserRole()){
            case "ADMIN":
                //new Object(request.getFirstName(), request.getLastName(), request.getEmail(), request.getMobile(), request.getPassword());
            case "CUSTOMER":
                Customer customer = new Customer(request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getMobile(),
                        LocalDate.now(),
                        false);

            case "VENDOR":
                Vendor vendor = new Vendor(false,
                        false,
                        false,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getMobile(),
                        LocalDate.now());
                vendorRepository.save(vendor);
        }
        return null;
    }

    @Override
    public SignInResponse signin(SignInRequest request) {
        UsernamePasswordAuthenticationToken unverifiedAuthToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(unverifiedAuthToken);
        //successfully verified
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtToken = JwtUtils.generateJwtToken(user);
        SignInResponse signInResponse = modelMapper.map(user, SignInResponse.class);
        signInResponse.setJwt(jwtToken);
        return signInResponse;
    }
}
