package com.demcare.demo.entities;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="association_institution_game")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class AssociationInstitutionGame implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_institution")
    @Getter @Setter
    private User institution;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_game")
    @Getter @Setter
    private Game game;

    @Getter
    @Setter
    private boolean desactivate;


    public boolean getDesactive() {
        return desactivate;
    }
}
