package com.fieldwire.assignment.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.fieldwire.assignment.model.Floorplan;

@Repository
public interface FloorplanRepository extends JpaRepository<Floorplan, UUID> {

    List<Floorplan> findAllByProjectId(UUID projectId);

    Optional<Floorplan> findByIdAndProjectId(UUID id, UUID projectId);

}
