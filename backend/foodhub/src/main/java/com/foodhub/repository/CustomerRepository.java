package com.foodhub.repository;

import java.util.Optional;

import com.foodhub.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.foodhub.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByEmail(String email);

    Optional<Customer> findByUser(User user);
}
