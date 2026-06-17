package com.salas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

    private Integer idRoom;

    @NotNull
    private String numberDto;

    @NotNull
    private String typeDto;

    @NotNull
    private BigDecimal priceDto;

    @NotNull
    private Boolean availableDto;
}
