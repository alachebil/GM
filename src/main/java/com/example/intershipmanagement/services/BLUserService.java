package com.example.intershipmanagement.services;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.repositories.IBLRepository;
import com.example.intershipmanagement.repositories.IBLUserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BLUserService implements IBLUserService {


    IBLUserRepository blUserRepository;
    IBLRepository blRepository;
    @Override
    public BLUser addBLUser(BLUser BLUser) {
        return blUserRepository.save(BLUser);
    }

    @Override
    public List<BLUser> getAllBLUser() {
        return blUserRepository.findAll();
    }

    @Override
    public BLUser getBLUserById(long idReserv) {
        return blUserRepository.findById(idReserv).get();
    }

    @Override
    public void deleteBLUser(long idReserv) {
        blUserRepository.deleteById(idReserv);

    }

    @Override
    public BLUser updateBLUser(BLUser BLUser) {
        return blUserRepository.save(BLUser);
    }

    @Override
    public BLUser AddBLUserAndAssign(BLUser blUserser, long IdBl) {
        BL Bl = blRepository.findById(IdBl).get();
        blUserser.setBl(Bl);

//// ************   Date   ******/*/-/-*/-/-/-/---*/
        // Obtenez la date actuelle
//        LocalDate dateActuelle = LocalDate.now();
// Affectez la date actuelle à la réservation
//        blUserser.setDate_reser(dateActuelle);
        return blUserRepository.save(blUserser);

    }

    @Override
    public String reserver(Long IdBl, BLUser blUser) {
        BL BL = blRepository.findById(IdBl).orElseThrow(() -> new RuntimeException("Événement non trouvé"));

//        if (BL.getNbrPlace() > 0) {
//            BL.setNbrPlace(BL.getNbrPlace() - 1);

        blRepository.save(BL);
//            if (BL.getNbrPlace() ==0){
//
            return "Réservation réussie";

    }

    @Override

        public Map<String, Integer> statBLUserParEvenement() {
            Map<String, Integer> statResult = new HashMap<>();
            List<BL> evenements = blRepository.findAll();

            for (BL evenement : evenements) {
                // Assuming 'reservations' is properly populated in the Evenement entity
                int reservationCount = evenement.getBLUsers() != null ? evenement.getBLUsers().size() : 0;
                statResult.put(evenement.getRefBl(), reservationCount);
            }

            return statResult;
        }


    @Override
    // Find all BLUser entities where the associated Utilisateur's 'nom' matches the given name
    public List<BL> getBLsForUserByNom(String nom) {
        List<BLUser> blUsers = blUserRepository.findByUserNom(nom);

        // Extract and return the corresponding unique BL entities
        Set<BL> uniqueBLs = blUsers.stream()
                .map(BLUser::getBl)  // Get associated BL from each BLUser
                .collect(Collectors.toSet());  // Using Set to remove duplicates

        // Convert the Set to a List to return it as a response
        return uniqueBLs.stream().collect(Collectors.toList());
    }



}


