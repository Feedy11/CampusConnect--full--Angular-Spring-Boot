package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.repository.ProjectRepository;
import com.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // Créer un projet
    public Project createProject(Project project, Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé !");
        }
        project.setOwner(userOpt.get());
        return projectRepository.save(project);
    }

    // Obtenir tous les projets
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Obtenir les projets d'un utilisateur
    public List<Project> getProjectsByUserId(Long userId) {
        return projectRepository.findByOwnerId(userId);
    }

    // Obtenir un projet par ID
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    // Mettre à jour un projet
    public Project updateProject(Long id, Project updatedProject, Long userId) {
        Optional<Project> existingProjectOpt = projectRepository.findById(id);
        if (existingProjectOpt.isEmpty()) {
            throw new RuntimeException("Projet non trouvé !");
        }
        
        Project existingProject = existingProjectOpt.get();
        
        // Vérifier que l'utilisateur est le propriétaire
        if (!existingProject.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier ce projet !");
        }

        existingProject.setTitle(updatedProject.getTitle());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setStartDate(updatedProject.getStartDate());
        existingProject.setEndDate(updatedProject.getEndDate());
        existingProject.setStatus(updatedProject.getStatus());
        existingProject.setTechnologies(updatedProject.getTechnologies());

        return projectRepository.save(existingProject);
    }

    // Supprimer un projet
    public void deleteProject(Long id, Long userId) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()) {
            throw new RuntimeException("Projet non trouvé !");
        }
        
        Project project = projectOpt.get();
        
        // Vérifier que l'utilisateur est le propriétaire
        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce projet !");
        }

        projectRepository.deleteById(id);
    }

    // Obtenir les projets par statut
    public List<Project> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }
}
