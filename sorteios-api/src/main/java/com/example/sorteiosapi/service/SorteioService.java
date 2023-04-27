package com.example.sorteiosapi.service;

import com.example.sorteiosapi.client.QuinaClient;
import com.example.sorteiosapi.client.dto.SorteioDTO;
import com.example.sorteiosapi.exception.SorteioException;
import com.example.sorteiosapi.model.SorteioModel;
import com.example.sorteiosapi.repository.SorteioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class SorteioService {

    private final QuinaClient client;
    private final SorteioRepository repository;

    public Mono<SorteioDTO> getUltimoSorteio() {
        return Mono.defer(() -> {
                    log.info("Buscando na CEF o ultimo sorteio");
                    return client.buscarResultadoSorteio();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<SorteioDTO> getSorteioByNumeroSorteio(Integer numeroSorteio) {
        return Mono.defer(() -> {
                    log.info("Buscando na CEF o sorteio - {}", numeroSorteio);
                    return client.buscarResultadoSorteio(numeroSorteio);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<SorteioModel> saveSorteio(Integer numeroSorteio) {
        var dataProximoSorteio = this.getUltimoSorteio()
                .map(SorteioDTO::getDataProximoSorteio);
        return this.getSorteioByNumeroSorteio(numeroSorteio)
                .switchIfEmpty(Mono.error(new SorteioException(numeroSorteio, dataProximoSorteio)))
                .flatMap(sorteio -> repository.save(SorteioDTO.convertToModel(new SorteioModel(), sorteio)));
    }

    public Mono<SorteioDTO> getSorteioByAposta(Integer numeroSorteio) {
        return Mono.defer(() -> {
                    log.info("Buscando sorteio no db - {}", numeroSorteio);
                    return repository.findByNumeroSorteio(numeroSorteio)
                            .switchIfEmpty(this.saveSorteio(numeroSorteio))
                            .switchIfEmpty(Mono.error(new NoSuchElementException("Sorteio não existe")))
                            .map(SorteioDTO::fromModel);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
