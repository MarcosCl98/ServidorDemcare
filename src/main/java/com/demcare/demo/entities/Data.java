package com.demcare.demo.entities;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="data")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Data implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @Getter @Setter
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_game")
    @Getter @Setter
    private Game game;

    @Getter
    @Setter
    private String scene;

    @Getter
    @Setter
    private double time_opened;

    @Getter
    @Setter
    private int number_clicks;

    @Getter
    @Setter
    private double max_speed;

    @Getter
    @Setter
    private double max_acceleration;

    @Getter
    @Setter
    private int number_errors;

}
