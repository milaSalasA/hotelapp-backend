package com.salas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO {

    private Integer idMenu;

    @NotNull
    private String icon;

    @NotNull
    private String name;

    @NotNull
    private String url;
}
