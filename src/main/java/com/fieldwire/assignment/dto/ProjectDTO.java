package com.fieldwire.assignment.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectDTO {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UUID id;
	private String name;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
