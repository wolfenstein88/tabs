package ru.max.tabs.dto;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TabFilter implements Serializable {

    @JsonPropertyDescription("Название заметки")
    private String title;

    @JsonPropertyDescription("URL заметки")
    private String url;

    @JsonPropertyDescription("Идентификатр метки")
    private BigInteger id;

}
