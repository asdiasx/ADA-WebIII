package com.example.sorteiosapi.repository;

import com.example.sorteiosapi.model.SorteioModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface SorteioRepository extends ReactiveCrudRepository<SorteioModel, Long> {
    Mono<SorteioModel> findByNumeroSorteio(Integer numero);
}
