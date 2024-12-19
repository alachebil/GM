package com.example.intershipmanagement.repositories;

import com.example.intershipmanagement.entities.BL;
import com.example.intershipmanagement.entities.BLUser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@ComponentScan
@Repository
public interface IBLUserRepository extends JpaRepository<BLUser,Long> {

    // Méthode pour récupérer les BL associés à un chauffeur par son nom (Ahmed)
//    @Query("SELECT b FROM BL b JOIN BLUser blu ON b.id = blu.bl.id JOIN Utilisateur u ON blu.user.id = u.id WHERE u.nom = :nom AND u.role = 'Chauffeur'")
//    List<BL> findBLsByChauffeur(@Param("nom") String nom);


    List<BLUser> findByUserNom(String nom);
}



