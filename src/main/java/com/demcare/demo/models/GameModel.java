package com.demcare.demo.models;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="game")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class GameModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String titulo;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private boolean desactivate;

}
