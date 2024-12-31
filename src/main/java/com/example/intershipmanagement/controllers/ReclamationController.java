package com.example.intershipmanagement.controllers;


import com.example.intershipmanagement.entities.Reclamation;
import com.example.intershipmanagement.repositories.ReclamationRepo;
import com.example.intershipmanagement.services.IReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/Reclamation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReclamationController {

    IReclamationService reclamationService;
    ReclamationRepo reclamationRepo;
    public static String uploadDirectory = System.getProperty("user.dir") + "/uploadUser";

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





    @PostMapping("/registerReclam")
    public ResponseEntity<Reclamation> registerAgent(@RequestParam("type") String type,
                                               @RequestParam("description") String description,
                                               @RequestParam("Ref_article") String Ref_article,
                                               @RequestParam("Poid") Long Poid,
                                               @RequestParam("Nbr") Long Nbr,
                                               @RequestParam("ImageJustificatif") MultipartFile file) throws IOException {
        Reclamation reclamation = new Reclamation();
        reclamation.setType(type);
        reclamation.setDescription(description);
        reclamation.setRef_article(Ref_article);
        reclamation.setNbr(Nbr);
        reclamation.setPoid(Poid);
        LocalDate dateActuelle = LocalDate.now();
        reclamation.setDateReclamation(dateActuelle);

        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        Path fileNameAndPath = Paths.get(uploadDirectory, uniqueFilename);
        if (!Files.exists(fileNameAndPath.getParent())) {
            Files.createDirectories(fileNameAndPath.getParent());
        }
        Files.write(fileNameAndPath, file.getBytes());
        reclamation.setImageJustificatif(uniqueFilename);
        Reclamation savedRecalamation = reclamationService.addreclamation(reclamation);
        return ResponseEntity.ok(savedRecalamation);
    }


    @PostMapping("/assign/{idBl}")
    public Reclamation reclamationAndAssign(@RequestBody Reclamation reclamation, @PathVariable("idBl") long idBl) {
        return reclamationService.reclamationAndAssign(reclamation, idBl);
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

//////      afficher les reclamations de bl donné par id
    @GetMapping("/retrieve-Reclamation-by-Bl/{idBl}")
    public List<Reclamation> retrieveReclamationByidBl(@PathVariable("idBl") Long idBl) {
        List<Reclamation> avis = reclamationService.retrieveReclamationByBl(idBl);
        return avis;
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