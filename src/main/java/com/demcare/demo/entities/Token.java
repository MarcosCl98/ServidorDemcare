package com.demcare.demo.entities;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="token")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Token implements Serializable {
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

    @Getter@Setter
    private String code;

}
