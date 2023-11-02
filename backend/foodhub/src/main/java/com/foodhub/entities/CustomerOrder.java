package com.foodhub.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder extends BaseEntity{

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="customer_id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="tiffin_id")
	private Tiffin tiffin;		

	// Make zero by default
	private int breakfastQuantity;

	private int lunchQuantity;
	
	private int dinnerQuantity;
	
	private LocalDate orderStartDate ;
	
	private LocalDate orderEndDate ;
	
	private String deliveryNote ;
}
