package com.example.resultadosapi.client;

import com.example.resultadosapi.client.dto.SorteioDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ApostasClient {

    private static final String CATALOGO_URI = "/api/v1/sorteios/";

    private final WebClient client;

    public ApostasClient(WebClient.Builder clientBuilder) {
        this.client = clientBuilder
                .baseUrl("http://localhost:8082")
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
                .uri(CATALOGO_URI + "/ultimo")
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
