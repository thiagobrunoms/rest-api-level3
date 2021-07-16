package com.socexample.dtos;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;


@Relation(collectionRelation = "cars")
@Data
@AllArgsConstructor
public class CarDTO extends RepresentationModel<CarDTO>{

	private String plate;

    private String name;
	
}
