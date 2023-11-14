package com.foodhub.service;

import com.foodhub.dto.SignInRequest;
import com.foodhub.dto.SignUpRequest;

public interface LoginService {
    String signup(SignUpRequest request);

    Object[] signin(SignInRequest request);
}
