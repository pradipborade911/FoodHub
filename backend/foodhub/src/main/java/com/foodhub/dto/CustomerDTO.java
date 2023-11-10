package com.foodhub.dto;

import com.foodhub.enums.UserRole;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id  ;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private LocalDate registerDate;
    private UserRole userRole;
    private String address;
    boolean isBlocked;
    private byte[] profilePic;
}
