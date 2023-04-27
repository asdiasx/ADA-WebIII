package com.example.resultadosapi.service;

import com.example.apostasapi.client.SorteiosClient;
import com.example.apostasapi.exception.DataJogoException;
import com.example.apostasapi.exception.NumeroSorteioException;
import com.example.apostasapi.model.ApostaDTO;
import com.example.apostasapi.model.ApostaModel;
import com.example.apostasapi.repository.ApostaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApostaService {

    private final ApostaRepository apostaRepository;
    private final SorteiosClient client;

    public Flux<ApostaDTO> getAllApostas() {
        return Flux.defer(() -> {
                    log.info("Buscando todas apostas no db");
                    return apostaRepository.findAll()
                            .map(ApostaDTO::fromModel);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<ApostaDTO> getApostaById(Long apostaId) {
        return Mono.defer(() -> {
                    log.info("Preparando buscando de aposta - {}", apostaId);
                    return this.getApostaModelById(apostaId)
                            .map(ApostaDTO::fromModel);
                })
                .subscribeOn(Schedulers.parallel());
    }

    public Mono<ApostaDTO> saveAposta(ApostaDTO apostaDTO) {
        return Mono.defer(() -> {
                    log.info("Preparando gravação da aposta - {}", apostaDTO);
                    return this.salvarAposta(Mono.just(new ApostaModel()), apostaDTO);
                })
                .subscribeOn(Schedulers.parallel());
    }

    public Mono<ApostaDTO> updateAposta(Long apostaId, ApostaDTO apostaDTO) {
        var apostaAModificar = getApostaModelById(apostaId);
        return Mono.defer(() -> {
                    log.info("Preparando atualização da aposta - {}", apostaAModificar);
                    return salvarAposta(apostaAModificar, apostaDTO);
                })
                .subscribeOn(Schedulers.parallel());
    }

    public Mono<Void> deleteAposta(Long apostaId) throws NoSuchElementException {
        return Mono.defer(() -> {
                    log.info("Removendo aposta do db - {}", apostaId);
                    return this.getApostaModelById(apostaId)
                            .flatMap(apostaRepository::delete);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<ApostaDTO> salvarAposta(Mono<ApostaModel> apostaModel, ApostaDTO apostaDTO) {
        return Mono.defer(() -> {
                    verificarDadosJogo(apostaDTO.getNumeroSorteio(), apostaDTO.getDataJogo());
                    log.info("Gravando aposta no db - {}", apostaModel);
                    return apostaModel
                            .map(aposta -> {
                                var entity = ApostaDTO.convertToModel(aposta, apostaDTO);
                                return apostaRepository.save(entity);
                            })
                            .flatMap(entityMono ->
                                    entityMono
                                            .map(ApostaDTO::fromModel));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private void verificarDadosJogo(Integer numeroSorteio, LocalDate dataJogo) {
        client.buscarResultadoSorteio()
                .doOnNext(ultimoSorteio -> {
                    if (ultimoSorteio.getNumeroConcursoProximo() < numeroSorteio) {
                        throw new NumeroSorteioException(ultimoSorteio.getNumeroConcursoProximo());
                    }
                    if (ultimoSorteio.getNumeroConcursoProximo().equals(numeroSorteio) &&
                            dataJogo.isAfter(ultimoSorteio.getDataProximoSorteio()))
                        throw new DataJogoException(numeroSorteio, ultimoSorteio.getDataProximoSorteio());
                })
                .then(client.buscarResultadoSorteio(numeroSorteio))
                .doOnNext(sorteio -> {
                            if (dataJogo.isAfter(sorteio.getDataSorteio()))
                                throw new DataJogoException(numeroSorteio, sorteio.getDataSorteio());
                        }
                )
                .subscribe();
    }

    private Mono<ApostaModel> getApostaModelById(Long apostaId) {
        return Mono.defer(() -> {
                    log.info("Buscando aposta no db - {}", apostaId);
                    return apostaRepository.findById(apostaId)
                            .switchIfEmpty(Mono.error(() -> new NoSuchElementException("Id da aposta não existe")));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
