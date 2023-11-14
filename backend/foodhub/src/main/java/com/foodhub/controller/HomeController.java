package com.foodhub.controller;

import com.foodhub.dto.SignInRequest;
import com.foodhub.dto.SignInResponse;
import com.foodhub.dto.SignUpRequest;
import com.foodhub.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest,  HttpServletResponse response){
        System.out.println(signInRequest.getUsername() + " " + signInRequest.getPassword());
        SignInResponse signInResponse =  (SignInResponse)loginService.signin(signInRequest)[0];
        String jwtToken = (String)loginService.signin(signInRequest)[1];
        Cookie jwtCookie = new Cookie("token", jwtToken);
        jwtCookie.setMaxAge(600);
        jwtCookie.setHttpOnly(true);
        response.setHeader("Set-Cookie", String.format("%s=%s; HttpOnly; SameSite=None", jwtCookie.getName(), jwtCookie.getValue()));
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signUp(@RequestPart("signUpRequest") SignUpRequest signUpRequest, @RequestPart("profilePic") MultipartFile profilePic){
        signUpRequest.setProfilePic(profilePic);
        String response =  loginService.signup(signUpRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
