package com.demcare.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="asociated_users")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class AsociatedUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_institution")
    @Getter @Setter
    private User userInstitution;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @Getter @Setter
    private User user;
}
