package com.demcare.demo.entities;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter
    private Long id;

    @Getter@Setter
    private String mail;

    @Getter@Setter
    private String password;

    @Getter@Setter
    private String name;

    @Getter@Setter
    private String surname;

    @Getter@Setter
    private String role;

}
