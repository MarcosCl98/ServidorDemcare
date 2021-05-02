package com.demcare.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class TestModel implements Serializable {
    @JsonProperty("id")
    @Getter@Setter
    private Long id;

    @JsonProperty("testName")
    @Getter@Setter
    private String testName;

}
