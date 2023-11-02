package com.foodhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @NotBlank(message = "email must be supplied")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "password must be supplied")
    private String password;
}
