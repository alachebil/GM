package com.example.intershipmanagement.repositories;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.BLUser;
import com.example.intershipmanagement.entities.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ILivraisonRepo extends JpaRepository<Livraison,Long> {


}
