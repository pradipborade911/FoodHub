package com.foodhub.dto;

import com.foodhub.enums.UserRole;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    private Long id  ;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private LocalDate registerDate;
    private UserRole userRole;
    private String address;
    private boolean isAvailable;
    private byte[] profilePic;
}
