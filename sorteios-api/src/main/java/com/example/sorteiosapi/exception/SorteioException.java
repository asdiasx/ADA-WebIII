package com.example.sorteiosapi.exception;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class SorteioException extends RuntimeException {

    private final Integer numeroSorteio;
    private final Mono<LocalDate> dataSorteio;
    private String dataSorteioString;

    @Override
    public String getMessage() {
        dataFormatada(dataSorteio);
        return "O sorteio de número " + numeroSorteio + " será realizado em " +
                dataSorteioString + " após as 20h";
    }

    private void dataFormatada(@NotNull Mono<LocalDate> dataSorteio) {
        dataSorteio
                .map(date -> this.dataSorteioString =
                        date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .subscribe();
    }
}
