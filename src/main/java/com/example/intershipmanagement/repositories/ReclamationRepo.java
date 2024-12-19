package com.example.intershipmanagement.repositories;

import com.example.intershipmanagement.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepo extends JpaRepository<Reclamation,Long> {

    List<Reclamation> findByBlId(long BlId);

}
