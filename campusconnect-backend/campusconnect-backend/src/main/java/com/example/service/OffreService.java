package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Offre;
import com.example.entity.User;
import com.example.repository.OffreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OffreService {

    @Autowired
    private OffreRepository offreRepository;

    // Consulter toutes les offres actives
    public List<Offre> getAllOffresActives() {
        return offreRepository.findByStatut("ACTIVE");
    }

    // Consulter toutes les offres (admin)
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    // Voir les détails d'une offre
    public Optional<Offre> getOffreById(Long id) {
        return offreRepository.findById(id);
    }

    // Ajouter une offre (admin)
    public Offre ajouterOffre(Offre offre, User admin) {
        offre.setAdmin(admin);
        return offreRepository.save(offre);
    }

    // Modifier une offre (admin)
    public Offre modifierOffre(Long id, Offre offreDetails) {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée !"));
        
        offre.setTitre(offreDetails.getTitre());
        offre.setDescription(offreDetails.getDescription());
        offre.setEntreprise(offreDetails.getEntreprise());
        offre.setLocalisation(offreDetails.getLocalisation());
        offre.setDuree(offreDetails.getDuree());
        offre.setDateDebut(offreDetails.getDateDebut());
        offre.setDateFin(offreDetails.getDateFin());
        offre.setCompetencesRequises(offreDetails.getCompetencesRequises());
        offre.setStatut(offreDetails.getStatut());
        
        return offreRepository.save(offre);
    }

    // Supprimer une offre (admin)
    public void supprimerOffre(Long id) {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée !"));
        offreRepository.delete(offre);
    }

    // Rechercher des offres
    public List<Offre> rechercherParEntreprise(String entreprise) {
        return offreRepository.findByEntrepriseContainingIgnoreCase(entreprise);
    }

    public List<Offre> rechercherParTitre(String titre) {
        return offreRepository.findByTitreContainingIgnoreCase(titre);
    }
}