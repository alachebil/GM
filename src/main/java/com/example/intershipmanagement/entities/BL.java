package com.example.intershipmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BL implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Ref_Bl;
    private String codeClient;

    private  String articleScan;

    private LocalDate dateDepot;

    private  LocalDate dateReceptionLiv;

    private boolean etatDepot;

    private boolean etatLivrer ;

    @OneToMany(mappedBy = "bl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BLUser> BLUsers;

    @OneToOne
    private Image imagecloud;

    @OneToMany(mappedBy = "bl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reclamation> reclamations;
}
