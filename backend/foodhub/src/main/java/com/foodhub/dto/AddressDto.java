package com.foodhub.dto;

import com.foodhub.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

	private Long id ;
	
	private UserRole role ;
	
	@NotBlank
	@Length(max=256 ,message="Address should  be less than 256 charachter")
	private String line1;

	@NotBlank
	@Length(max=256 ,message="Address should be less than 256 charachter")
	private String line2;
	
	@NotBlank
	private String city;
	
	@NotBlank
	@Length(min=6, max=6 ,message = "Pincode should have only 6 characters")
	private String pincode;
	
	@NotBlank
	private String state;
}
