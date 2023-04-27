package com.example.resultadosapi.controller;

import com.example.resultadosapi.model.ResultadoResponse;
import com.example.resultadosapi.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/resultados")
public class ResultadoRestController {

    private final ResultadoService service;

    @GetMapping("/{apostaID}")
    public ResultadoResponse getResultado(@PathVariable("apostaID") Long apostaId) {
        return service.getResultadoByAposta(apostaId);
    }
}
