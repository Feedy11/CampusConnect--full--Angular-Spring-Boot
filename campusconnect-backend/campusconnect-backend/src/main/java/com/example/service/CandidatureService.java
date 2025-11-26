package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Candidature;
import com.example.entity.Offre;
import com.example.entity.User;
import com.example.repository.CandidatureRepository;
import com.example.repository.OffreRepository;
import com.example.repository.UserRepository;

import java.util.List;

@Service
public class CandidatureService {

    @Autowired
    private CandidatureRepository candidatureRepository;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private UserRepository userRepository;

    // Postuler à une offre
    public Candidature postuler(Long offreId, Long etudiantId, String lettreMotivation) {
        User etudiant = userRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé !"));
        
        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée !"));

        // Vérifier si l'étudiant a déjà postulé
        if (candidatureRepository.existsByEtudiantAndOffre(etudiant, offre)) {
            throw new RuntimeException("Vous avez déjà postulé à cette offre !");
        }

        Candidature candidature = new Candidature();
        candidature.setEtudiant(etudiant);
        candidature.setOffre(offre);
        candidature.setLettreMotivation(lettreMotivation);

        return candidatureRepository.save(candidature);
    }

    // Voir mes candidatures (étudiant)
    public List<Candidature> getMesCandidatures(Long etudiantId) {
        User etudiant = userRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé !"));
        return candidatureRepository.findByEtudiant(etudiant);
    }

    // Voir les candidatures d'une offre (admin)
    public List<Candidature> getCandidaturesParOffre(Long offreId) {
        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée !"));
        return candidatureRepository.findByOffre(offre);
    }

    // Toutes les candidatures (admin)
    public List<Candidature> getAllCandidatures() {
        return candidatureRepository.findAll();
    }

    // Mettre à jour le statut d'une candidature (admin)
    public Candidature updateStatut(Long candidatureId, String nouveauStatut) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée !"));
        
        candidature.setStatut(nouveauStatut);
        return candidatureRepository.save(candidature);
    }
}