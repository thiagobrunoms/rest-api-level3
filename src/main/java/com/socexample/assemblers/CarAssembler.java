package com.socexample.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.socexample.dtos.CarDTO;
import com.socexample.dtos.UserDTO;
import com.socexample.entities.Car;
import com.socexample.entities.User;
import com.socexample.resources.UserResource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@Component
public class CarAssembler implements RepresentationModelAssembler<Car, CarDTO> {

	@Override
	public CarDTO toModel(Car entity) {
		CarDTO userDTO = new CarDTO(entity.getPlate(), entity.getName());
        userDTO.add(linkTo(methodOn(UserResource.class).findUserCarDetails(entity.getUser().getCode(), entity.getPlate())).withSelfRel());
        userDTO.add(linkTo(methodOn(UserResource.class).deleteCar(entity.getUser().getCode(), entity.getPlate())).withRel("DELETE"));
		return userDTO;
	}

}
