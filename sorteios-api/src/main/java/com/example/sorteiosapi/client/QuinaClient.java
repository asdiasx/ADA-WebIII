package com.example.sorteiosapi.client;

import com.example.sorteiosapi.client.dto.SorteioDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class QuinaClient {

    private static final String CATALOGO_URI = "/quina/";

    private final WebClient client;

    public QuinaClient(WebClient.Builder clientBuilder) {
        this.client = clientBuilder
                .baseUrl("https://servicebus2.caixa.gov.br/portaldeloterias/api")
                .build();
    }

    public Mono<SorteioDTO> buscarResultadoSorteio(Integer numeroSorteio) {
        return this.client
                .get()
                .uri(CATALOGO_URI + numeroSorteio)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(result -> {
                    if (result.statusCode().is2xxSuccessful()) {
                        return result.bodyToMono(SorteioDTO.class);
                    } else {
                        return Mono.error(new RuntimeException("Erro na chamada"));
                    }
                });
    }

    public Mono<SorteioDTO> buscarResultadoSorteio() {
        return this.client
                .get()
                .uri(CATALOGO_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(result -> {
                    if (result.statusCode().is2xxSuccessful()) {
                        return result.bodyToMono(SorteioDTO.class);
                    } else {
                        return Mono.error(new RuntimeException("Erro na chamada"));
                    }
                });
    }
}
