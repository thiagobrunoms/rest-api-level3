package com.socexample.services;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException.Conflict;
import org.springframework.web.client.HttpClientErrorException.Forbidden;

import com.socexample.assemblers.CarAssembler;
import com.socexample.assemblers.UserAssembler;
import com.socexample.dtos.CarDTO;
import com.socexample.dtos.UserDTO;
import com.socexample.entities.Car;
import com.socexample.entities.User;
import com.socexample.exceptions.EntityAlreadyExistsException;
import com.socexample.repositories.CarRepository;
import com.socexample.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
    private UserAssembler userAssembler;

	@Autowired
    private CarAssembler carAssembler;

    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;

	@Override
	public CollectionModel<UserDTO> findAll(int page, int size, String[] sort, String dir) {
		PageRequest pageRequest;
		Sort.Direction direction;
		
		if (sort == null) {
			pageRequest = PageRequest.of(page, size);
		} else {
			if (dir.equalsIgnoreCase("asc")) {
				direction = Sort.Direction.ASC;
			} else {
				direction = Sort.Direction.DESC;
			}
			
			pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));
		}
		
		Page<User> users = userRepository.findAll(pageRequest);
		
		if (!CollectionUtils.isEmpty(users.getContent())) 
			return pagedResourcesAssembler.toModel(users, userAssembler);
		
		return null;
	}

	@Override
	public UserDTO findByCode(String code) {
		User user = userRepository.findByCode(code).orElse(null);
		
		if (user == null)
			return userAssembler.toModel(user);
		
		return null;
	}

	@Override
	public CollectionModel<CarDTO> findUserCars(String code) {
		User user = userRepository.findByCode(code).orElse(null);
		
		if (user != null && (!CollectionUtils.isEmpty(user.getCars())))
			return carAssembler.toCollectionModel(user.getCars());
		
		return null;
	}

	@Transactional
	@Override
	public UserDTO insert(User user) throws EntityAlreadyExistsException {
		User foundUser = userRepository.findByCode(user.getCode()).orElse(null);
		
		if (foundUser != null) throw new EntityAlreadyExistsException("Entidade com code " + user.getCode() + " já existe!");
		
		user.getCars().forEach(car -> car.setUser(user));
		
		return userAssembler.toModel(userRepository.save(user));
	}

	@Transactional
	@Override
	public UserDTO update(User user) throws EntityAlreadyExistsException {
		System.out.println("Buscando usuario por codigo " + user.getCode());
		User foundUser = userRepository.findByCode(user.getCode()).orElse(null);
		
		System.out.println("found user code " + foundUser.getCode() + " recevied code: " + user.getCode());
		if (foundUser != null && user.getCode() != foundUser.getCode()) throw new EntityAlreadyExistsException("Entidade com code " + user.getCode() + " já existe!");
		
		if (foundUser == null) return null;
		
		user.setId(foundUser.getId());
		
		return insert(user);
	}
	
	

	@Transactional
	@Override
	public UserDTO delete(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public CarDTO findUserCarDetails(String code, String plate) throws EntityNotFoundException {
		System.out.println("findUserCarDetails fired!");
		Car car = carRepository.findByPlate(plate).orElse(null);
		
		System.out.println("user de carro " + car.getUser());
		
		if (car == null) {
			throw new EntityNotFoundException("Veículo com placa " + plate + " não encontrado!");
		}
		
		return carAssembler.toModel(car);
	}

	@Transactional
	@Override
	public void deleteCar(String plate) throws EntityNotFoundException {
		Car car = carRepository.findByPlate(plate).orElse(null);
		
		if (car == null) throw new EntityNotFoundException("Veículo com placa " + plate + " não encontrado!");

		carRepository.deleteByPlate(plate);
	}

}
