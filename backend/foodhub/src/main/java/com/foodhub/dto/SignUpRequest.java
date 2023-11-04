package com.foodhub.dto;

import com.foodhub.entities.Address;
import com.foodhub.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "username must be supplied")
    private String username;

    @NotBlank(message = "first name must be supplied")
    private String firstName;

    @NotBlank(message = "last name must be supplied")
    private String lastName;

    @NotBlank(message = "email must be supplied")
    @Email(message = "Invalid email format")
    private String email;

    private String mobile;

    @NotBlank(message = "password must be supplied")
    private String password;

    private String userRole;

    private Address address;
}
