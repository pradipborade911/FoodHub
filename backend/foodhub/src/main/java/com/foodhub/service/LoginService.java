package com.foodhub.service;

import com.foodhub.dto.SignInRequest;
import com.foodhub.dto.SignInResponse;
import com.foodhub.dto.SignUpRequest;

public interface LoginService {
    String signup(SignUpRequest request);

    SignInResponse signin(SignInRequest request);
}
