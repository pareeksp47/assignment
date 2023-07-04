package com.fieldwire.assignment.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fieldwire.assignment.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

}
