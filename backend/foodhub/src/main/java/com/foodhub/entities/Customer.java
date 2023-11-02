package com.foodhub.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {
	
	String firstName;
	String lastName;
	String email;
	String mobile;
	LocalDate registerDate;
	@Value("false")
	boolean isBlocked;
	
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="address_id")
//	Address address;	
	
}
