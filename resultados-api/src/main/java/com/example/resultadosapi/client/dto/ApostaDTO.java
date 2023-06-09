package com.example.resultadosapi.client.dto;

import com.example.resultadosapi.validation.GameConstraint;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApostaDTO {
    private Long id;
    private Integer numeroSorteio;
    @GameConstraint
    private int[] dezenas;
    private LocalDate dataJogo;
}
