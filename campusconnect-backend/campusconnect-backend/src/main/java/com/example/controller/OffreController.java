package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Offre;
import com.example.entity.User;
import com.example.service.OffreService;
import com.example.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/offres")
@CrossOrigin(origins = "http://localhost:4200")
public class OffreController {

    @Autowired
    private OffreService offreService;

    // Consulter toutes les offres actives (accessible par tous)
    @GetMapping("/actives")
    public ResponseEntity<List<Offre>> getOffresActives() {
        return ResponseEntity.ok(offreService.getAllOffresActives());
    }

    // Consulter toutes les offres (admin)
    @GetMapping
    public ResponseEntity<List<Offre>> getAllOffres() {
        return ResponseEntity.ok(offreService.getAllOffres());
    }

    // Voir les détails d'une offre
    @GetMapping("/{id}")
    public ResponseEntity<?> getOffreById(@PathVariable Long id) {
        return offreService.getOffreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter une offre (admin)
    @PostMapping
    public ResponseEntity<?> ajouterOffre(@RequestBody OffreRequest request) {
        try {
            // Note: Dans une vraie application, l'admin serait récupéré depuis le token JWT
            User admin = new User();
            admin.setId(request.getAdminId());
            
            Offre offre = offreService.ajouterOffre(request.getOffre(), admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(offre);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Modifier une offre (admin)
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierOffre(@PathVariable Long id, @RequestBody Offre offre) {
        try {
            Offre updatedOffre = offreService.modifierOffre(id, offre);
            return ResponseEntity.ok(updatedOffre);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Supprimer une offre (admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerOffre(@PathVariable Long id) {
        try {
            offreService.supprimerOffre(id);
            return ResponseEntity.ok(new SuccessResponse("Offre supprimée avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Rechercher par entreprise
    @GetMapping("/recherche/entreprise")
    public ResponseEntity<List<Offre>> rechercherParEntreprise(@RequestParam String entreprise) {
        return ResponseEntity.ok(offreService.rechercherParEntreprise(entreprise));
    }

    // Rechercher par titre
    @GetMapping("/recherche/titre")
    public ResponseEntity<List<Offre>> rechercherParTitre(@RequestParam String titre) {
        return ResponseEntity.ok(offreService.rechercherParTitre(titre));
    }

    // Classes internes pour les requêtes et réponses
    static class OffreRequest {
        private Offre offre;
        private Long adminId;

        public Offre getOffre() {
            return offre;
        }

        public void setOffre(Offre offre) {
            this.offre = offre;
        }

        public Long getAdminId() {
            return adminId;
        }

        public void setAdminId(Long adminId) {
            this.adminId = adminId;
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

    static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
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