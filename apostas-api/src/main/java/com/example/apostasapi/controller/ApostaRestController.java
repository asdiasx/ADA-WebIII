package com.example.apostasapi.controller;

import com.example.apostasapi.model.ApostaDTO;
import com.example.apostasapi.service.ApostaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/apostas")
public class ApostaRestController {

    private final ApostaService apostaService;

    @GetMapping
    public Flux<ApostaDTO> getAllApostas() {
        return Flux.defer(() -> {
                    log.info("Processando busca de todas apostas");
                    return apostaService.getAllApostas();
                })
                .subscribeOn(Schedulers.parallel());
    }

    @GetMapping("/{apostaId}")
    public Mono<ApostaDTO> getApostaById(@PathVariable Long apostaId) {
        return apostaService.getApostaById(apostaId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApostaDTO> saveAposta(@Valid @RequestBody ApostaDTO apostaDTO) {
        return apostaService.saveAposta(apostaDTO);
    }

    @PutMapping("/{apostaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApostaDTO> updateAposta(@PathVariable Long apostaId, @Valid @RequestBody ApostaDTO apostaDTO) {
        return Mono.defer(() -> {
                    log.info("Processando atualização - {}", apostaId);
                    return apostaService.updateAposta(apostaId, apostaDTO);
                })
                .subscribeOn(Schedulers.parallel());
    }

    @DeleteMapping("/{apostaId}")
    public Mono<Void> deleteAposta(@PathVariable Long apostaId) {
        return apostaService.deleteAposta(apostaId);
    }
}
