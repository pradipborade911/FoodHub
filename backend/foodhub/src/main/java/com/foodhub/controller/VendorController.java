package com.foodhub.controller;

import com.foodhub.dto.SignInRequest;
import com.foodhub.dto.SignInResponse;
import com.foodhub.dto.SignUpRequest;
import com.foodhub.dto.VendorDTO;
import com.foodhub.service.LoginService;
import com.foodhub.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/vendor")
@RestController
public class VendorController {
    @Autowired
    VendorService vendorService;

    @GetMapping("/approvedVendors")
    public ResponseEntity<?> getApprovedVendors(){
        List<VendorDTO> vendors =  vendorService.getAllApprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    @GetMapping("/unapprovedVendors")
    public ResponseEntity<?> getUnapprovedVendors(){
        List<VendorDTO> vendors =  vendorService.getAllUnapprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    @GetMapping("/getAllVendors")
    public ResponseEntity<?> getAllVendors(){
        List<VendorDTO> vendors =  vendorService.getAllApprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping("/{vendorId}") // Get Vendors by Id
    public ResponseEntity<?> getVendorById(@PathVariable Long vendorId){
        return new ResponseEntity<>(vendorService.getVendorById(vendorId), HttpStatus.OK) ;
    }
    @PutMapping
    public ResponseEntity<?> updateVendor(@PathVariable VendorDTO vendor){
        return new ResponseEntity<> (vendorService.updateVendor(vendor), HttpStatus.OK) ;
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<?> deleteVendorById(@PathVariable Long vendorId){
        return new ResponseEntity<> (vendorService.deleteVendorById(vendorId), HttpStatus.OK) ;
    }

    @PatchMapping("/approve/{vendorId}")
    public ResponseEntity<?> approveVendorById(@PathVariable Long vendorId){
        return new ResponseEntity<>(vendorService.approveVendorById(vendorId), HttpStatus.OK) ;
    }

    @PatchMapping("/available/{vendorId}")
    public ResponseEntity<?> changeVendorAvailablibility(@PathVariable Long vendorId){
        return new ResponseEntity<>(vendorService.changeAvailability(vendorId), HttpStatus.OK) ;
    }

    @PatchMapping("/block/{vendorId}")
    public ResponseEntity<?> changeBlockingStatus(@PathVariable Long vendorId){
        return new ResponseEntity<> (vendorService.changeBlockingStatus(vendorId), HttpStatus.OK) ;
    }

}
