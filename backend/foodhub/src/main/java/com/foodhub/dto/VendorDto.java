package com.foodhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorDto {
	private long id;
	
	@NotBlank(message = "Firstname can't be blank")
	@Length(min=2 , message = "Firstname should have atleast 5 character")
	private String firstName;
	
	@NotBlank(message = "Lastname can't be blank")
	@Length(min=2 , message = "Lastname should have atleast 5 character")
	private String lastName;
	
	@NotBlank
	@Length(max=10 , message = "mobile should have atleast 10 digit")
	private String mobile;
	
	//@NotBlank
	//@Length(max=256 ,message= "address must have minimum 256 character")
	private AddressDto  address;
	
	@Length(min = 5,max=20,message = "Invalid password length")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@Email
	private String email;
		
	private boolean isAvailable;
	
	private boolean isBlocked;
	
}
