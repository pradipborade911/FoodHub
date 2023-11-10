package com.foodhub.entities;

import java.time.LocalDate;

import com.foodhub.security.User;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;


@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor extends BaseEntity {

	@Value("false")
	private boolean isVerified;
	@Value("true")
	private boolean isAvailable;
	@Value("false")
	private boolean isBlocked;
	
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private LocalDate registerDate;
	@OneToOne(cascade = CascadeType.ALL)@JoinColumn(name="user_id")
	private User user;

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="address_id")
//	private Address address;
//	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<SubscriptionPlan> plans = new HashSet<SubscriptionPlan>();


}
