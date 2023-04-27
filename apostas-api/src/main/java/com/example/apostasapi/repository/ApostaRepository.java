package com.example.apostasapi.repository;


import com.example.apostasapi.model.ApostaModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ApostaRepository extends ReactiveCrudRepository<ApostaModel, Long> {

}
