package com.demcare.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="test")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class TestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    @Getter@Setter@EqualsAndHashCode.Include
    private Long id;

    @Getter@Setter
    @Column(name = "test_name")
    private String testName;
}

