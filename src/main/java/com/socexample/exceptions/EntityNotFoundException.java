package com.socexample.exceptions;

public class EntityNotFoundException extends RuntimeException {
	
	EntityNotFoundException(String message) {
		super(message);
	}

}
