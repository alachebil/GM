package com.example.intershipmanagement.repositories;

import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.entities.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILivraisonRepo extends JpaRepository<Livraison,Long> {

}
