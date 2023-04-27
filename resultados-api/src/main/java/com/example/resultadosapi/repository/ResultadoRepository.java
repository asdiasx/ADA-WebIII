package com.example.resultadosapi.repository;

import com.example.resultadosapi.model.ResultadoModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ResultadoRepository extends ReactiveCrudRepository<ResultadoModel, Long> {
    Mono<ResultadoModel> findByApostaId(Long apostaId);
}
