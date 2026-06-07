package com.salas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

    private Integer idRoom;

    @NotNull
    private Integer numberDto;

    @NotNull
    private String typeDto;

    @NotNull
    private Double priceDto;

    @NotNull
    private Boolean availableDto;
}
