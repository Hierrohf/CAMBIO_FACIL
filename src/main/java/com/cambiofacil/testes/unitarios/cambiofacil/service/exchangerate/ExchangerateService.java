package com.cambiofacil.testes.unitarios.cambiofacil.service.exchangerate;

import com.cambiofacil.testes.unitarios.cambiofacil.config.exchangerapi.ExchangerateApiProperties;
import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.request.ExchangerateRequestDto;
import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response.ExchangerateApiResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExchangerateService {

    private final WebClient webClient;
    private final ExchangerateApiProperties properties;

    public ExchangerateService(WebClient webClient, ExchangerateApiProperties properties){
        this.webClient = webClient;
        this.properties = properties;
    }

    public Mono<ExchangerateApiResponseDto> convert(ExchangerateRequestDto requestDto){
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/convert")
                                .queryParam("access_key", properties.getKey())
                                .queryParam("from", requestDto.from())
                                .queryParam("to", requestDto.to())
                                .queryParam("amount", requestDto.amount())
                                .build())
                .retrieve()
                .bodyToMono(ExchangerateApiResponseDto.class)
                .doOnNext(System.out::println);
    }
}