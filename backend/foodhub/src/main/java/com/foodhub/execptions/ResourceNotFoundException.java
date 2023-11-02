package com.foodhub.execptions;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super(message) ;
	}
}
