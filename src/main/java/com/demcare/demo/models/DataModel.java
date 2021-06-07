package com.demcare.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DataModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5637206190860391304L;
    @JsonProperty("timeOpened")
    @Getter
    @Setter
    private String timeOpened;

    @JsonProperty("urlPlayer")
    @Getter
    @Setter
    private String urlPlayer;

    @JsonProperty("scene")
    @Getter
    @Setter
    private String scene;

    @JsonProperty("game")
    @Getter
    @Setter
    private Long game;

}
