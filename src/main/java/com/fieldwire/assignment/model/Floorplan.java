package com.fieldwire.assignment.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "floorplan", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name", "project" }) })
public class Floorplan {

	@Id
	@Column(name = "id", columnDefinition = "BINARY(16)")
	@GeneratedValue
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "original_url", nullable = false)
	private String originalUrl;

	@Column(name = "thumb_url", nullable = false)
	private String thumbUrl;

	@Column(name = "large_url", nullable = false)
	private String largeUrl;

	@JoinColumn(name = "project", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
	private Project project;

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

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getLargeUrl() {
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
