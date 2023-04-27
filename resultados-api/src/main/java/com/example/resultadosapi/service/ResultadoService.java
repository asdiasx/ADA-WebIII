package com.example.resultadosapi.service;

import com.example.resultadosapi.client.ApostasClient;
import com.example.resultadosapi.client.SorteiosClient;
import com.example.resultadosapi.client.dto.ApostaDTO;
import com.example.resultadosapi.client.dto.Premio;
import com.example.resultadosapi.client.dto.SorteioDTO;
import com.example.resultadosapi.model.ResultadoModel;
import com.example.resultadosapi.model.ResultadoResponse;
import com.example.resultadosapi.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ResultadoService {

    private final ResultadoRepository repository;
    private final ApostasClient apostasClient;
    private final SorteiosClient sorteiosClient;

    public Mono<ResultadoResponse> getResultadoByAposta(Long apostaId) {
        return repository.findByApostaId(apostaId)
                .flatMap(this::resultadoModelToResponse)
                .switchIfEmpty(apostasClient.buscarAposta(apostaId)
                        .switchIfEmpty(Mono.error(new NoSuchElementException("Id da aposta não existe")))
                        .flatMap(apostaDTO -> sorteiosClient.buscarResultadoSorteio(apostaDTO.getNumeroSorteio())
                                .flatMap(sorteioDTO -> {
                                    ResultadoModel resultado = criarResultado(apostaDTO, sorteioDTO);
                                    return saveResultado(resultado)
                                            .flatMap(this::resultadoModelToResponse);
                                })));
    }

    private Mono<ResultadoResponse> resultadoModelToResponse(ResultadoModel resultadoModel) {
        return Mono.just(resultadoModel)
                .flatMap(resultado -> apostasClient.buscarAposta(resultado.getApostaId())
                        .switchIfEmpty(Mono.error(new NoSuchElementException("Id da aposta não existe")))
                        .flatMap(apostaDTO -> sorteiosClient.buscarResultadoSorteio(apostaDTO.getNumeroSorteio())
                                .map(sorteioDTO -> new ResultadoResponse(
                                        resultado.getNumeroSorteio(),
                                        sorteioDTO.getDataSorteio(),
                                        sorteioDTO.getDezenasSorteadas(),
                                        apostaDTO.getDezenas(),
                                        sorteioDTO.isAcumulado(),
                                        resultado.getPontuacao(),
                                        resultado.getValorPremio()
                                ))));
    }

    private ResultadoModel criarResultado(ApostaDTO aposta, SorteioDTO sorteio) {
        int[] dezenasApostadas = aposta.getDezenas();
        int[] dezenasSorteadas = sorteio.getDezenasSorteadas();
        Integer pontuacao = calcularPontuacao(dezenasApostadas, dezenasSorteadas);
        BigDecimal valorPremio = calcularValorPremio(pontuacao, sorteio.getPremios());
        ResultadoModel resultadoModel = new ResultadoModel();
        resultadoModel.setNumeroSorteio(sorteio.getNumeroSorteio());
        resultadoModel.setApostaId(aposta.getId());
        resultadoModel.setPontuacao(pontuacao);
        resultadoModel.setValorPremio(valorPremio);
        return resultadoModel;
    }

    private Mono<ResultadoModel> saveResultado(ResultadoModel resultadoModel) {
        return repository.save(resultadoModel);
    }

    private BigDecimal calcularValorPremio(Integer pontuacao, List<Premio> premios) {
        return premios.stream()
                .filter(premio -> pontuacao.equals(Integer.parseInt(premio.getDescricao().substring(0, 1))))
                .map(Premio::getValorPremio)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }

    private Integer calcularPontuacao(int[] dezenasApostadas, int[] dezenasSorteadas) {
        return 10 - (int) Stream.of(Arrays.stream(dezenasApostadas), Arrays.stream(dezenasSorteadas))
                .flatMap(IntStream::boxed)
                .distinct()
                .count();
    }
}
