package com.example.intershipmanagement.repositories;

import com.example.intershipmanagement.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepo extends JpaRepository<Reclamation,Long> {
}
