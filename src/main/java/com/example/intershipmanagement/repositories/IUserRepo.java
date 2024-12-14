package com.example.intershipmanagement.repositories;

import com.example.intershipmanagement.entities.Reclamation;
import com.example.intershipmanagement.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<Utilisateur,Long> {



}
