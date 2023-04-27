package com.example.resultadosapi.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Premio {
    @JsonProperty("descricaoFaixa")
    private String descricao;
    private Integer faixa;
    private Integer numeroDeGanhadores;
    private BigDecimal valorPremio;
}
