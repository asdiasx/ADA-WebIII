package com.example.resultadosapi.repository;

import com.example.resultadosapi.client.dto.ApostaDTO;
import com.example.resultadosapi.client.dto.ApostaModel;
import com.example.resultadosapi.client.dto.SorteioDTO;
import com.example.resultadosapi.model.ResultadoModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

public interface ResultadoRepository extends ReactiveCrudRepository<ResultadoModel, Long> {


    Mono<ResultadoModel> findByApostaAndSorteioAndPontuacaoAndValorPremio(ApostaDTO apostaDTO,
                                                                          SorteioDTO sorteioDTO,
                                                                          Integer pontuacao,
                                                                          BigDecimal valorPremio);
}
