package com.socexample.exceptions.problemadetails;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.socexample.exceptions.EntityAlreadyExistsException;
import com.socexample.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ProblemDetailsExceptionHandler {
	
	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<?> handleConflictingException(EntityAlreadyExistsException ex) {
		Map<String, Object> problemDetailsData = new HashMap<>();
		problemDetailsData.put("type", URI.create("https://myapp.com/probs/entity-already-exists"));
		problemDetailsData.put("title", "A entidade já existe");
		problemDetailsData.put("detail", ex.getMessage());
		problemDetailsData.put("status", "CONFLICT");
		
		HttpHeaders h = new HttpHeaders();
		h.add("Content-Type", "application/problem+json");
		
		return new ResponseEntity<>(problemDetailsData, h, HttpStatus.CONFLICT); 
		
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex) {
		Map<String, Object> problemDetailsData = new HashMap<>();
		problemDetailsData.put("type", URI.create("https://myapp.com/probs/entity-not-found"));
		problemDetailsData.put("title", "A entidade não existe");
		problemDetailsData.put("detail", ex.getMessage());
		problemDetailsData.put("status", "NOT FOUND");
		
		HttpHeaders h = new HttpHeaders();
		h.add("Content-Type", "application/problem+json");
		
		return new ResponseEntity<>(problemDetailsData, h, HttpStatus.NOT_FOUND); 
		
	}
	
}
