package com.example.intershipmanagement.controllers;


import com.example.intershipmanagement.entities.Reclamation;
import com.example.intershipmanagement.repositories.ReclamationRepo;
import com.example.intershipmanagement.services.IReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/Reclamation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReclamationController {

    IReclamationService reclamationService;
    ReclamationRepo reclamationRepo;


    @GetMapping("/retrieve-all-reclamation")
    public List<Reclamation> reclamation(){
        List<Reclamation> listreclamationses = reclamationService.retrieveAllreclamation();
        return listreclamationses;
    }
    @GetMapping("/retrieve-reclamation/{idreclamation}")
    public Reclamation retrievereclamation(@PathVariable("idreclamation") Long reclamationId) {
        Reclamation reclamation = reclamationService.retrievereclamation(reclamationId);
        return reclamation;
    }


    @PostMapping("/add-reclamation")
    public Reclamation addreclamation(@RequestBody Reclamation e) {
        Reclamation reclamation = reclamationService.addreclamation(e);
        return reclamation;
    }

    @PostMapping("/assign/{idEvent}")
    public Reclamation reclamationAndAssign(@RequestBody Reclamation reclamation, @PathVariable("idEvent") long idEvent) {
        return reclamationService.reclamationAndAssign(reclamation, idEvent);
    }

    @DeleteMapping("/remove-reclamation/{reclamation-id}")
    public void removeChambre(@PathVariable("reclamation-id") Long reclamationId) {
        reclamationService.removereclamation(reclamationId);
    }

    @PutMapping("/modify-reclamation")
    public Reclamation modifyreclamation(@RequestBody Reclamation e) {
        Reclamation reclamation = reclamationService.modifyreclamation(e);
        return reclamation;

    }


    @GetMapping("/statEventparreclamation")
    public Map<String, Integer> statReservationParEvenement(){
        return reclamationService.statreclamationParBl();
    }





    @PostMapping("/analyse-reclamation/{reclamation-id}")
    public ResponseEntity<String> analysereclamation(@PathVariable("reclamation-id") Long reclamationId){
        Reclamation reclamation = reclamationRepo.findById(reclamationId).get();
        if (reclamation == null || reclamation.getDescription() == null || reclamation.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().body("Le contenu de l'reclamation est vide.");
        }

        String tonalite = reclamationService.analyserTonalite(reclamation.getDescription());
        return ResponseEntity.ok(tonalite);
    }




}