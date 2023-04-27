package com.example.apostasapi.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
