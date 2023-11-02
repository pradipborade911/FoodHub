package com.foodhub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foodhub.dto.CustomerOrderDto;
import com.foodhub.entities.CustomerOrder;
import com.foodhub.entities.Tiffin;
import com.foodhub.execptions.ResourceNotFoundException;
import com.foodhub.repository.CustomerOrderRepository;
import com.foodhub.repository.TiffinRepository;

@Transactional
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {
	
	@Autowired
	private CustomerOrderRepository customerOrderRepo;
	
	@Autowired
	private TiffinRepository tiffinRepo;
	
	@Autowired
	private ModelMapper modelMapper;


	@Override

	public CustomerOrderDto createOrder(CustomerOrderDto customerOrderDto) {
		CustomerOrder customerOrder = modelMapper.map(customerOrderDto, CustomerOrder.class);
		customerOrderRepo.save(customerOrder);
		return customerOrderDto;
	}
	

	@Override

	public List<CustomerOrderDto> getAllCustomerOrders() {
		List<CustomerOrderDto> allCustomerOrdersList = customerOrderRepo.findAll()
				.stream().map(customerOrder->modelMapper.map(customerOrder, CustomerOrderDto.class))
				.collect(Collectors.toList());
		return allCustomerOrdersList;
	}
	
	@Override

	public CustomerOrderDto getCustomerOrderById(Long customerOrderId) {
		CustomerOrder customerOrder = customerOrderRepo.findById(customerOrderId).
				orElseThrow(()-> new ResourceNotFoundException("Invalid customer order ID"));
		
		return modelMapper.map(customerOrder, CustomerOrderDto.class);
	}

	@Override
	public String deleteCustomerOrderById(Long customerOrderId) {
		CustomerOrder customerOrder = customerOrderRepo.findById(customerOrderId).
				orElseThrow(()-> new ResourceNotFoundException("Invalid Customer Order ID"));
		
		customerOrderRepo.delete(customerOrder);
		return "Order with Order Id: " + customerOrder.getId() +  "Customer Name: " + customerOrder.getCustomer().getFirstName() +
				" " + customerOrder.getCustomer().getLastName() + " has been removed!";
	}
	
	@Override
	public List<CustomerOrderDto> getCustomerOrdersByTiffinId(Long tiffinId) {
		List<CustomerOrderDto> customerOrdersList = customerOrderRepo.findByTiffinId(tiffinId)
				.stream().map(customerOrder->modelMapper.map(customerOrder, CustomerOrderDto.class))
				.collect(Collectors.toList());
		return customerOrdersList;
	}
	
	@Override
	public List<CustomerOrderDto> getCustomerOrdersByCustomerId(Long customerId) {
		return  customerOrderRepo.findByCustomerId(customerId)
				.orElseThrow(()->new ResourceNotFoundException("Error occured while fetching customer orders"))
				.stream()
				.map(customerOrder->modelMapper.map(customerOrder, CustomerOrderDto.class))
				.collect(Collectors.toList());
	}


	@Override

	public List<CustomerOrderDto> getCustomerOrdersByVendorId(Long vendorId) {
		List<Tiffin> tiffinListForVendor = tiffinRepo.findAllByVendorId(vendorId)
				.orElseThrow(()-> new ResourceNotFoundException("This vendor has no Tiffins!")) ;		
		  
		List<CustomerOrder> customerOrderList = new ArrayList<>() ;
		  
		tiffinListForVendor.forEach(tiffin-> {
			  customerOrderList.addAll(customerOrderRepo.findByTiffinId(tiffin.getId()));
		  });
		 
		return customerOrderList.stream()
			   .map( cs -> modelMapper.map(cs, CustomerOrderDto.class))
			   .collect(Collectors.toList());
	}
	
	
	

}
