package com.foodhub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodhub.entities.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

	List<CustomerOrder> findByTiffinId(Long tiffinId);
	
	Optional<List<CustomerOrder>> findByCustomerId(Long customerId);
	



	
}
