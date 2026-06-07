package com.salas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRoom;

    @Column(nullable = false, length = 3)
    private Integer number;

    @Column(nullable = false, length = 30)
    private String type;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean available;
}
