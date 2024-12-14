package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.Livraison;
import com.example.intershipmanagement.repositories.ILivraisonRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LivraisonService implements  ILivraisonService {

    ILivraisonRepo livraisonRepo;

    @Override
    public Livraison addLivraison(Livraison livraison) {
        return livraisonRepo.save(livraison);
    }

    @Override
    public List<Livraison> getAllLivraison() {
        return livraisonRepo.findAll();
    }

    @Override
    public Livraison getLivraisonById(long idLivraison) {
        return livraisonRepo.findById(idLivraison).get();
    }

    @Override
    public void deleteLivraison(long idLivraison) {
        livraisonRepo.deleteById(idLivraison);
    }

    @Override
    public Livraison updateLivraison(Livraison livraison) {
        return livraisonRepo.save(livraison);
    }
}
