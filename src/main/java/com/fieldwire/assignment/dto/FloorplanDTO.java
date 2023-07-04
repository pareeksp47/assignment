package com.fieldwire.assignment.dto;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FloorplanDTO {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UUID id;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String name;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String originalUrl;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String thumbUrl;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String largeUrl;

	@JsonProperty("files")
	private List<MultipartFile> files;

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

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
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

}
