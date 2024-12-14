package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.Livraison;

import java.util.List;

public interface ILivraisonService {


    Livraison addLivraison(Livraison livraison);
    List<Livraison> getAllLivraison();
    Livraison getLivraisonById(long idLivraison);
    void deleteLivraison(long idLivraison);
    Livraison updateLivraison(Livraison livraison);
//    public List<Livraison> getEventsByUserOrderByParticipation(Long userId);
}
