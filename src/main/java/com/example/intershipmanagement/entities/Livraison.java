package com.example.intershipmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Livraison implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private LocalDate Date_LivraisonClient ;

    private boolean Etat_Livraison ;

    private String confirmation;

    @OneToOne
    private BL bl;

}