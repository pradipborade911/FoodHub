package com.foodhub.entities;

import com.foodhub.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode
@Entity
public class Address extends BaseEntity {
	
	@Enumerated(EnumType.STRING) // col : varchar => enum constant name
	private AddressType addressType;
	
	private String line1;

	private String line2;

	private String city;

	private Integer pincode;

	private String state;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer ;
	
	@ManyToOne
	@JoinColumn(name="vendor_id")
	private Vendor vendor ;

	@Override
	public String toString() {
		return addressType + ": " + line1 + ", " + line2 + ", " + city + " " + pincode + ", " + state;
	}
}
