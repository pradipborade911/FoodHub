package com.foodhub.controller;

import com.foodhub.dto.SignInRequest;
import com.foodhub.dto.SignInResponse;
import com.foodhub.dto.UserProfile;
import com.foodhub.dto.SignUpRequest;
import com.foodhub.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    LoginService loginService;
    @GetMapping("/home")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest){
        System.out.println(signInRequest.getUsername() + " " + signInRequest.getPassword());
        SignInResponse response =  loginService.signin(signInRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/signup")
    public ResponseEntity<?> signIn(@RequestBody SignUpRequest  signUpRequest){
        String response =  loginService.signup(signUpRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
