package com.demcare.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="asociation_institution_user")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class AssociationInstitutionUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name = "id_institution")
    @Getter @Setter
    private User userInstitution;

    @OneToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name = "id_user")
    @Getter @Setter
    private User user;
}
