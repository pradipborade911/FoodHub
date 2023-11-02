package com.foodhub.entities;

import java.time.LocalDate;
import com.foodhub.enums.FoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tiffin extends BaseEntity{

	private String name;

	private double price;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	private FoodType foodType;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="vendor_id")
	private Vendor vendor ;
	
	private LocalDate availableFrom ;
	
	private LocalDate availableTo ;
	
	// From LSB TO MSB -> the positions of the binary maps to 
	// Breakfast, Lunch and Dinner. 
	// The three digit binary is then converted to integer which corresponds to a unique configuration.
	private int breakLunchDinner ;
}
