package com.example.intershipmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor

@NoArgsConstructor
@Builder
public class BL implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refBl;
    private String codeClient;
    private  String articleScan;
    private LocalDate datechauffeur;
    private LocalDate dateDepot;
    private  LocalDate dateReceptionLiv;
    private boolean etatDepot;
    private boolean etatLivrer ;

    @OneToMany(mappedBy = "bl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BLUser> BLUsers;

    @OneToMany(mappedBy = "bl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reclamation> reclamations;

    @OneToOne
    @JsonIgnore
    private Livraison livraison;

}