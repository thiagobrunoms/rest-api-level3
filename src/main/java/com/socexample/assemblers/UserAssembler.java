package com.socexample.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.socexample.dtos.UserDTO;
import com.socexample.entities.User;
import com.socexample.resources.UserResource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@Component
public class UserAssembler implements RepresentationModelAssembler<User, UserDTO> {

	@Override
	public UserDTO toModel(User entity) {
		UserDTO userDTO = new UserDTO(entity.getName(), entity.getSurname(), entity.getCode(), entity.getAddress());
        userDTO.add(linkTo(methodOn(UserResource.class).findUserCars(entity.getCode())).withRel("cars"));
        userDTO.add(linkTo(methodOn(UserResource.class).findByCode(entity.getCode())).withSelfRel());
        userDTO.add(linkTo(methodOn(UserResource.class).updateUser(entity.getCode(), entity)).withRel("PUT"));

		return userDTO;
	}

}
