package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.entities.Livraison;
import com.example.intershipmanagement.entities.Utilisateur;

import java.time.LocalDate;
import java.util.List;

public interface ILivraisonService {


    Livraison addLivraison(Livraison livraison);
    List<Livraison> getAllLivraison();
    Livraison getLivraisonById(long idLivraison);
    void deleteLivraison(long idLivraison);
    Livraison updateLivraison(Livraison livraison);

    public void  addLivraisonPositiveandAssignBl(Livraison livraison, Long BlId);
    public void addLivraisonNegativeandAssignBl(Livraison livraison, Long BlId);
    public void  validerLivraison( Long BlId);
    public void  ReclamerLivraison( Long BlId);

    Livraison getLivraisonByBLId(long idBL);

}
