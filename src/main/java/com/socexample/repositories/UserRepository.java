package com.socexample.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socexample.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByCode(String code);

}
