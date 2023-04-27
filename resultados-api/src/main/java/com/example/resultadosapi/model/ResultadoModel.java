package com.example.resultadosapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("resultados")
public class ResultadoModel {
    @Id
    private Long id;
    private Long apostaId;
    private Integer numeroSorteio;
    private Integer pontuacao;
    private BigDecimal valorPremio;
}
