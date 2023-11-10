package com.foodhub.controller;

import com.foodhub.dto.SignInRequest;
import com.foodhub.dto.SignInResponse;
import com.foodhub.dto.SignUpRequest;
import com.foodhub.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("http://localhost:3000/")
public class HomeController {
    @Autowired
    LoginService loginService;
    @GetMapping("/home")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest){
        System.out.println(signInRequest.getUsername() + " " + signInRequest.getPassword());
        SignInResponse response =  loginService.signin(signInRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signUp(@RequestPart("signUpRequest") SignUpRequest signUpRequest, @RequestPart("profilePic") MultipartFile profilePic){
        signUpRequest.setProfilePic(profilePic);
        String response =  loginService.signup(signUpRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
