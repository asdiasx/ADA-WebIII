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

    public static ApostaDTO fromModel(ApostaModel apostaModel) {
        return new ApostaDTO(apostaModel.getId(), apostaModel.getNumeroSorteio(), apostaModel.getDezenas(), apostaModel.getDataJogo());
    }

    public static ApostaModel convertToModel(ApostaModel apostaModel, ApostaDTO apostaDTO) {
        apostaModel.setNumeroSorteio(apostaDTO.getNumeroSorteio());
        apostaModel.setDezenas(apostaDTO.getDezenas());
        apostaModel.setDataJogo(apostaDTO.getDataJogo());
        return apostaModel;
    }
}
