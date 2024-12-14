package com.example.intershipmanagement.controllers;

import com.example.intershipmanagement.entities.Livraison;
import com.example.intershipmanagement.entities.Reclamation;
import com.example.intershipmanagement.services.ILivraisonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
