package com.socexample.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socexample.dtos.CarDTO;
import com.socexample.dtos.UserDTO;
import com.socexample.entities.User;
import com.socexample.exceptions.EntityAlreadyExistsException;
import com.socexample.exceptions.EntityNotFoundException;
import com.socexample.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
									 @RequestParam(required = false, defaultValue = "3") Integer size,
									 @RequestParam(required = false) String[] sort,
									 @RequestParam(required = false, defaultValue = "asc") String dir) {
		
		CollectionModel<UserDTO> users = userService.findAll(page, size, sort, dir);
		
		if (users != null) {
			return ResponseEntity.ok(users);
		}
		
		
		return ResponseEntity.noContent().build();
		
	}
	
	
	@GetMapping("/{code}")
	public ResponseEntity<?> findByCode(@PathVariable String code) {
		UserDTO userDTO = userService.findByCode(code);
		
		if (userDTO != null) return ResponseEntity.ok(userDTO);
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("{code}/cars")
	public ResponseEntity<?> findUserCars(@PathVariable String code) {
		System.out.println( "obtendo carros de " + code);
		CollectionModel<CarDTO> cars = userService.findUserCars(code);
		
		if (cars != null) return ResponseEntity.ok(cars);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{code}/cars/{plate}")
	public ResponseEntity<?> findUserCarDetails(@PathVariable String code, @PathVariable String plate) {
		System.out.println( "obtendo carros de " + code);
		
		try {
			CarDTO car = userService.findUserCarDetails(code, plate);
			
			return ResponseEntity.ok(car);
		} catch (EntityNotFoundException e) {
			throw e;
		}
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE})
	public ResponseEntity<?> createUser(@RequestBody User user) {
		try {
			UserDTO userDTO = userService.insert(user);
			
			return ResponseEntity.created(userDTO.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(userDTO);
		} catch (EntityAlreadyExistsException e) {
			throw e;
		}
	}
	
	@PutMapping("{code}")
	public ResponseEntity<?> updateUser(@PathVariable String code, @RequestBody User user) {
		try {
			user.setCode(code);
			UserDTO updated = userService.update(user);
			
			if (updated != null) {
				return ResponseEntity.ok().body(updated);
			} else {
				return ResponseEntity.notFound().build();
			}
		
		} catch (EntityAlreadyExistsException e) {
			throw e;
		}
	}
	
	@DeleteMapping("{code}/cars/{plate}")
	public ResponseEntity<?> deleteCar(@PathVariable String code, @PathVariable String plate) {
		try {
			userService.deleteCar(plate);
			
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			throw e;
		}
	}
	
	
}
