package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Offre;

import java.util.List;

public interface OffreRepository extends JpaRepository<Offre, Long> {
    List<Offre> findByStatut(String statut);
    List<Offre> findByEntrepriseContainingIgnoreCase(String entreprise);
    List<Offre> findByTitreContainingIgnoreCase(String titre);
}
