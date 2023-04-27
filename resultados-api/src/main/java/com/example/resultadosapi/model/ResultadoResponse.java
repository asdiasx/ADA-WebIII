package com.example.resultadosapi.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResultadoResponse {
    private Integer numeroSorteio;
    private LocalDate dataSorteio;
    private int[] dezenasSorteadas;
    private int[] dezenasApostadas;
    private boolean acumulado;
    private Integer pontuacao;
    private BigDecimal valorPremio;
}
