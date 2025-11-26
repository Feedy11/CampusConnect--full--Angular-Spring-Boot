package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Candidature;
import com.example.entity.User;
import com.example.entity.Offre;

import java.util.List;
import java.util.Optional;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByEtudiant(User etudiant);
    List<Candidature> findByOffre(Offre offre);
    Optional<Candidature> findByEtudiantAndOffre(User etudiant, Offre offre);
    boolean existsByEtudiantAndOffre(User etudiant, Offre offre);
}