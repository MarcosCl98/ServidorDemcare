package com.demcare.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserModel implements Serializable {
    @JsonProperty("id")
    @Getter@Setter
    private Long id;

    @JsonProperty("mail")
    @Getter@Setter
    private String mail;

    @JsonProperty("password")
    @Getter@Setter
    private String password;

    @JsonProperty("name")
    @Getter@Setter
    private String name;

    @JsonProperty("surname")
    @Getter@Setter
    private String surname;

    @JsonProperty("role")
    @Getter@Setter
    private String role;
}
