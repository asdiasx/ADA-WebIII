package com.example.apostasapi.model;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("apostas")
public class ApostaModel {
    @Id
    private Long id;
    private Integer numeroSorteio;
    private int[] dezenas;
    private LocalDate dataJogo;
}
