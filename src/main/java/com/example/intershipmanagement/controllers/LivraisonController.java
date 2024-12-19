package com.example.intershipmanagement.controllers;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.Livraison;
import com.example.intershipmanagement.entities.Reclamation;
import com.example.intershipmanagement.services.ILivraisonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Livraison")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LivraisonController {

    ILivraisonService livraisonService;

    @GetMapping("/retrieve-all-Livraison")
    public List<Livraison> reclamation(){
        return livraisonService.getAllLivraison();
    }


    @PostMapping("/addLivraisonPositiveandAssignBlPositive/{idBl}")
    public void addLivraisonandAssignBl(@RequestBody Livraison livraison , @PathVariable long idBl){
        livraisonService.addLivraisonPositiveandAssignBl(livraison,idBl);
    }

    @PostMapping("/addLivraisonNegativeandAssignBlPositive/{idBl}")
    public void addLivraisonNegativeandAssignBlPositive(@RequestBody Livraison livraison , @PathVariable long idBl){
        livraisonService.addLivraisonNegativeandAssignBl(livraison,idBl);
    }

    // mettre l'etat de livraison positive (true)
    @PostMapping("/validerLivraison/{idBl}")
    public void validerLivraison( @PathVariable long idBl){
        livraisonService.validerLivraison(idBl);
    }
    @PostMapping("/ReclamerLivraison/{idBl}")
    public void ReclamerLivraison( @PathVariable long idBl){
        livraisonService.ReclamerLivraison(idBl);
    }



    @GetMapping("/retrieve-Livraison-ParBl/{idBl}")
    public Livraison getLivraisonByBl(@PathVariable long idBl){
        return livraisonService.getLivraisonByBLId(idBl);
    }


}
