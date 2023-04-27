package com.example.sorteiosapi.controller;

import com.example.sorteiosapi.client.dto.SorteioDTO;
import com.example.sorteiosapi.model.SorteioModel;
import com.example.sorteiosapi.repository.SorteioRepository;
import com.example.sorteiosapi.service.SorteioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sorteios")
public class SorteioRestController {

    private final SorteioService service;
    private final SorteioRepository repository;

    @GetMapping("/ultimo")
    public Mono<SorteioDTO> getSorteioCadastradoByNumeroSorteio() {
        return Mono.defer(() -> {
                    log.info("Preparando busca do ultimo sorteio");
                    return service.getUltimoSorteio();
                })
                .subscribeOn(Schedulers.parallel());
    }
    @GetMapping("/{numSorteio}")
    public Mono<SorteioDTO> getSorteioCadastradoByNumeroSorteio(@PathVariable("numSorteio") Integer numeroSorteio) {
        return Mono.defer(() -> {
                    log.info("Preparando busca do sorteio - {}", numeroSorteio);
                    return service.getSorteioByAposta(numeroSorteio);
                })
                .subscribeOn(Schedulers.parallel());
    }

    @GetMapping("/testdb/all")
    public Flux<SorteioModel> getAll(){
        return repository.findAll();
    }
}
