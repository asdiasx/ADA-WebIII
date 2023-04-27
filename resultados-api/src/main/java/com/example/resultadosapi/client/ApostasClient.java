package com.example.resultadosapi.client;

import com.example.resultadosapi.client.dto.ApostaDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ApostasClient {

    private static final String CATALOGO_URI = "/api/v1/apostas/";

    private final WebClient client;

    public ApostasClient(WebClient.Builder clientBuilder) {
        this.client = clientBuilder
                .baseUrl("http://localhost:8081")
                .build();
    }

    public Mono<ApostaDTO> buscarAposta(Long numeroAposta) {
        return this.client
                .get()
                .uri(CATALOGO_URI + numeroAposta)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(result -> {
                    if (result.statusCode().is2xxSuccessful()) {
                        return result.bodyToMono(ApostaDTO.class);
                    } else {
                        return Mono.error(new RuntimeException("Erro na chamada"));
                    }
                });
    }
}
