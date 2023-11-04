package com.foodhub.repository;

import java.util.List;
import java.util.Optional;

import com.foodhub.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.foodhub.entities.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

	Optional<List<Vendor>> findByIsVerified(boolean b);

	Optional<Vendor> findByEmail(String email);

    Optional<Vendor> findByUser(User user);
}
