package com.demcare.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="association_carer_player")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class AssociationCarerPlayer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carer")
    @Getter @Setter
    private User carerUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_player")
    @Getter @Setter
    private User playerUser;
}
