package com.socexample.exceptions;

public class EntityNotFoundException extends RuntimeException {
	
	public EntityNotFoundException(String message) {
		super(message);
	}

}
