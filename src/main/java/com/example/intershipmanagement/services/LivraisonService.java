package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.entities.Livraison;
import com.example.intershipmanagement.entities.Utilisateur;
import com.example.intershipmanagement.repositories.IBLRepository;
import com.example.intershipmanagement.repositories.ILivraisonRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LivraisonService implements  ILivraisonService {

    ILivraisonRepo livraisonRepo;
    IBLRepository blRepository;

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

    @Override
    public void addLivraisonPositiveandAssignBl(Livraison livraison, Long BlId) {
        BL bl = blRepository.findById(BlId).get();
        LocalDate dateActuelle = LocalDate.now();
        livraison.setDateLivraisonClient(dateActuelle);
//        livraison.setEtatLivraison(true);
        livraison.setBl(bl);
        bl.setLivraison(livraison);
        livraisonRepo.save(livraison);
    }

    @Override
    public void addLivraisonNegativeandAssignBl(Livraison livraison, Long BlId) {
        BL bl = blRepository.findById(BlId).get();
        LocalDate dateActuelle = LocalDate.now();
        livraison.setDateLivraisonClient(dateActuelle);
//        livraison.setEtatLivraison(null);
        livraison.setBl(bl);
        bl.setLivraison(livraison);
        livraisonRepo.save(livraison);
    }

    @Override
    public void validerLivraison( Long BlId) {
        BL bl = blRepository.findById(BlId).get();
        Livraison livraison=livraisonRepo.findById(bl.getLivraison().getId()).get();
        livraison.setEtatLivraison(true);
        livraisonRepo.save(livraison);
    }

    @Override
    public void ReclamerLivraison( Long BlId) {
        BL bl = blRepository.findById(BlId).get();
        Livraison livraison=livraisonRepo.findById(bl.getLivraison().getId()).get();
        livraison.setEtatLivraison(false);
        livraisonRepo.save(livraison);

    }


    ////  nchouf la livraison d'une bl mou3ayna
    @Override
    public Livraison getLivraisonByBLId(long idBL) {
        BL bl = blRepository.findById(idBL)
                .orElseThrow(() -> new RuntimeException("BL not found with ID: " + idBL));

        // Directly return the associated Livraison
        return bl.getLivraison();

    }
}


