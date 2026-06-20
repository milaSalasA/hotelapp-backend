package com.salas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFile;

    @Column(nullable = false, length = 50)
    private String fileName;

    @Column(nullable = false, length = 20)
    private String fileType;

    @Column(nullable = false)
    private byte[] content;

}
