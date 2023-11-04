package com.foodhub.dto;

import com.foodhub.enums.UserRole;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private Long id  ;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private UserRole userRole;
    private String address;
}
