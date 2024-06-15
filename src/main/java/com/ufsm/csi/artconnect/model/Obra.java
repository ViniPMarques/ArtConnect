package com.ufsm.csi.artconnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idobra;
    @Column(name = "filename")
    private String fileName;
    @Column(name = "filepath")
    private String filePath;
    private Long idartista;
}
