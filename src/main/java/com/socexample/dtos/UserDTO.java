package com.socexample.dtos;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Relation(collectionRelation = "users")
@Data
@AllArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO>{

	private String name;
	
	private String surname;

    private String code;

    private String address;
	
}
