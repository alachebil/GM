package com.example.intershipmanagement.entities;


        import com.fasterxml.jackson.annotation.JsonIgnore;
        import jakarta.persistence.*;
        import lombok.*;

        import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "event")
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private  String description;

    private  String Ref_article ;

    private Long Poid;

    private  Long Nbr ;

    private LocalDate DateReclamation;

    private String ImageJustificatif;

//    private  String BL_ID ;

    @ManyToOne
    @JsonIgnore
    private BL bl;

}
