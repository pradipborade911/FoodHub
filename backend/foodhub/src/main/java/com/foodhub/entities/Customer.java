package com.foodhub.entities;

import java.time.LocalDate;

import com.foodhub.security.User;
import jakarta.persistence.*;
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
	@OneToOne(cascade = CascadeType.ALL)@JoinColumn(name="user_id")
	private User user;
	@Value("false")
	boolean isBlocked;
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="address_id")
//	Address address;	
	
}
