package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Candidature;
import com.example.service.CandidatureService;

import java.util.List;

@RestController
@RequestMapping("/api/candidatures")
@CrossOrigin(origins = "http://localhost:4200")
public class CandidatureController {

    @Autowired
    private CandidatureService candidatureService;

    // Postuler à une offre (étudiant)
    @PostMapping("/postuler")
    public ResponseEntity<?> postuler(@RequestBody CandidatureRequest request) {
        try {
            Candidature candidature = candidatureService.postuler(
                    request.getOffreId(),
                    request.getEtudiantId(),
                    request.getLettreMotivation()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(candidature);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Voir mes candidatures (étudiant)
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<?> getMesCandidatures(@PathVariable Long etudiantId) {
        try {
            List<Candidature> candidatures = candidatureService.getMesCandidatures(etudiantId);
            return ResponseEntity.ok(candidatures);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Voir les candidatures d'une offre (admin)
    @GetMapping("/offre/{offreId}")
    public ResponseEntity<?> getCandidaturesParOffre(@PathVariable Long offreId) {
        try {
            List<Candidature> candidatures = candidatureService.getCandidaturesParOffre(offreId);
            return ResponseEntity.ok(candidatures);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Toutes les candidatures (admin)
    @GetMapping
    public ResponseEntity<List<Candidature>> getAllCandidatures() {
        return ResponseEntity.ok(candidatureService.getAllCandidatures());
    }

    // Mettre à jour le statut d'une candidature (admin)
    @PutMapping("/{id}/statut")
    public ResponseEntity<?> updateStatut(@PathVariable Long id, @RequestBody StatutRequest request) {
        try {
            Candidature candidature = candidatureService.updateStatut(id, request.getStatut());
            return ResponseEntity.ok(candidature);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Classes internes pour les requêtes
    static class CandidatureRequest {
        private Long offreId;
        private Long etudiantId;
        private String lettreMotivation;

        public Long getOffreId() {
            return offreId;
        }

        public void setOffreId(Long offreId) {
            this.offreId = offreId;
        }

        public Long getEtudiantId() {
            return etudiantId;
        }

        public void setEtudiantId(Long etudiantId) {
            this.etudiantId = etudiantId;
        }

        public String getLettreMotivation() {
            return lettreMotivation;
        }

        public void setLettreMotivation(String lettreMotivation) {
            this.lettreMotivation = lettreMotivation;
        }
    }

    static class StatutRequest {
        private String statut;

        public String getStatut() {
            return statut;
        }

        public void setStatut(String statut) {
            this.statut = statut;
        }
    }

    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
