package com.socexample.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socexample.entities.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

	public Optional<Car> findByPlate(String plate);
	
	public void deleteByPlate(String plate);
	
}
