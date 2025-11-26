package com.example.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "projects")
public class Project {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String title;

	    @Column(length = 1000)
	    private String description;

	    @Column(nullable = false)
	    private LocalDate startDate;

	    private LocalDate endDate;

	    private String status; // EN_COURS, TERMINE, PLANIFIE

	    private String technologies; // Liste des technologies utilisées

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User owner;

	    public Project() {}

	    public Project(String title, String description, LocalDate startDate, 
	                   LocalDate endDate, String status, String technologies, User owner) {
	        this.title = title;
	        this.description = description;
	        this.startDate = startDate;
	        this.endDate = endDate;
	        this.status = status;
	        this.technologies = technologies;
	        this.owner = owner;
	    }

	    // --- Getters & Setters ---
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public LocalDate getStartDate() {
	        return startDate;
	    }

	    public void setStartDate(LocalDate startDate) {
	        this.startDate = startDate;
	    }

	    public LocalDate getEndDate() {
	        return endDate;
	    }

	    public void setEndDate(LocalDate endDate) {
	        this.endDate = endDate;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getTechnologies() {
	        return technologies;
	    }

	    public void setTechnologies(String technologies) {
	        this.technologies = technologies;
	    }

	    public User getOwner() {
	        return owner;
	    }

	    public void setOwner(User owner) {
	        this.owner = owner;
	    }

}
