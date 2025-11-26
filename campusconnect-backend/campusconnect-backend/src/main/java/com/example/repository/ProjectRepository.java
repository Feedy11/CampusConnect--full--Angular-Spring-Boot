package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Project;
import com.example.entity.User;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User owner);
    List<Project> findByOwnerId(Long ownerId);
    List<Project> findByStatus(String status);
}
