package com.example.intershipmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Livraison implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private LocalDate dateLivraisonClient;

    private Boolean etatLivraison;

    private String confirmationImage;

    @OneToOne(mappedBy = "livraison")
    @JsonIgnore
    private BL bl;

}