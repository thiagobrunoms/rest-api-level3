package com.socexample.services;

import org.springframework.hateoas.CollectionModel;


import com.socexample.dtos.CarDTO;
import com.socexample.dtos.UserDTO;
import com.socexample.entities.User;
import com.socexample.exceptions.EntityAlreadyExistsException;
import com.socexample.exceptions.EntityNotFoundException;

public interface UserService {
	
	public CollectionModel<UserDTO> findAll(int page, int size, String[] sort, String dir);
	
	public UserDTO findByCode(String code);
	
	public CollectionModel<CarDTO> findUserCars(String code);
	
	public CarDTO findUserCarDetails(String code, String plate) throws EntityNotFoundException;
	
	public UserDTO insert(User user) throws EntityAlreadyExistsException;
	
	public UserDTO update(User user) throws EntityAlreadyExistsException;
	
	public void deleteCar(String plate) throws EntityNotFoundException;
	
	public UserDTO delete(User user);
	
}
