package com.demcare.demo.models;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter
    private Long id;

    @Getter@Setter
    private String mail;

    @Getter@Setter
    private String password;

    @Transient
    @Getter@Setter
    private String passwordConfirm;

    @Getter@Setter
    private String name;

    @Getter@Setter
    private String institucion;

    @Getter@Setter
    private String surname;

    @Getter@Setter
    private String role;

    @Getter@Setter
    private boolean suspend;

    @Getter@Setter
    private String photos;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/img/" + mail+ "/" + photos;
    }


}
