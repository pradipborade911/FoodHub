package com.foodhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
	
	private Long id;
	
	@NotBlank(message = "Firstname can't be blank")
	@Length(min=2 , message = "Firstname should have atleast 5 character")
	private String firstName;
	
	@NotBlank(message = "Lastname can't be blank")
	@Length(min=2 , message = "Lastname should have atleast 5 character")
	private String lastName;
	
	@NotBlank(message = "Email can't be blank")
	@Email(message = "Invalid email format")
	private String email;
	
	@Length(min = 5,max=20,message = "Invalid password length")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@NotBlank(message = "mobile can't be blank")
	@Length(min=10 , message = "mobile should have atleast 10 digit")
	private String mobile;
	
	private boolean isBlocked;
	
	@NotBlank
	@Length(max=256 ,message= "address must have minimum 256 character")
	private AddressDto address;
}
