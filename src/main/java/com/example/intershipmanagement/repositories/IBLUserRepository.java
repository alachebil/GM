package com.example.intershipmanagement.repositories;

import com.example.intershipmanagement.entities.BLUser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@ComponentScan
@Repository
public interface IBLUserRepository extends JpaRepository<BLUser,Long> {


}

