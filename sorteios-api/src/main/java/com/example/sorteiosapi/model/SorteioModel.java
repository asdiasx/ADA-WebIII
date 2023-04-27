package com.example.sorteiosapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("sorteios")
public class SorteioModel {
    @Id
    private Long id;
    private Integer numeroSorteio;
    private Integer numeroConcursoProximo;
    private boolean acumulado;
    private LocalDate dataSorteio;
    private LocalDate dataProximoSorteio;
    private int[] dezenasSorteadas;
    private String premios;
    private BigDecimal valorAcumuladoProximoConcurso;
}
