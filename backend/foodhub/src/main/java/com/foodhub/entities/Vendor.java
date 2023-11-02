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
	
	String firstName;
	String lastName;
	String email;
	String mobile;
	LocalDate registerDate;


//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="address_id")
//	private Address address;
//	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<SubscriptionPlan> plans = new HashSet<SubscriptionPlan>();


}
