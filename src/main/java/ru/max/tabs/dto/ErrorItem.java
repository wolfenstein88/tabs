package ru.max.tabs.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorItem implements Serializable {

    @JsonPropertyDescription("Код ошибки")
    private String code;

    @JsonPropertyDescription("Сообщение об ошибке")
    private String message;

}
