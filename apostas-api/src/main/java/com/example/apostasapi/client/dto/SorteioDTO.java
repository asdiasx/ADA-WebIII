package com.example.apostasapi.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SorteioDTO {

    @JsonProperty("numero")
    private Integer numeroSorteio;
    private Integer numeroConcursoProximo;
    private boolean acumulado;
    private LocalDate dataSorteio;
    private LocalDate dataProximoSorteio;
    @JsonProperty("listaDezenas")
    private int[] dezenasSorteadas;
    @JsonProperty("listaRateioPremio")
    private List<Premio> premios;
    private BigDecimal valorAcumuladoProximoConcurso;

    @JsonProperty("dataApuracao")
    private void convertDate(String dataString) {
        this.dataSorteio = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @JsonProperty("dataProximoConcurso")
    private void convertDateProximoSorteio(String dataString) {
        this.dataProximoSorteio = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
