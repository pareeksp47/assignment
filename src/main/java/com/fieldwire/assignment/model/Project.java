package com.fieldwire.assignment.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;

/**
 * @description: Project entity
 *
 */
@Entity
@Table(name = "project")
public class Project {

	@Id
	@Column(name = "id", columnDefinition = "BINARY(16)")
	@GeneratedValue
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Floorplan> floorplans;

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

	public List<Floorplan> getFloorplans() {
		return floorplans;
	}

	public void setFloorplans(List<Floorplan> floorplans) {
		this.floorplans = floorplans;
	}

}
