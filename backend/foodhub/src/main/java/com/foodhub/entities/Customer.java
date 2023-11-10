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
	
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private LocalDate registerDate;
	@OneToOne(cascade = CascadeType.ALL)@JoinColumn(name="user_id")
	private User user;
	@Value("false")
	boolean isBlocked;
	String profilePicPath;
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="address_id")
//	Address address;	
	
}
