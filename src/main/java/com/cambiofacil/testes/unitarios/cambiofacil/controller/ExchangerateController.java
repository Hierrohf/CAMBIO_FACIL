package com.cambiofacil.testes.unitarios.cambiofacil.controller;

import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.request.ExchangerateRequestDto;
import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response.ExchangerateResponseDto;
import com.cambiofacil.testes.unitarios.cambiofacil.service.exchangerate.ExchangerateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/exchangerate")
public class ExchangerateController {

    private final ExchangerateService service;

    public ExchangerateController(ExchangerateService service){
        this.service = service;
    }

    @GetMapping("/convert")
    public Mono<ResponseEntity<ExchangerateResponseDto>> exchangerateConvert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount
    ) {
        return service
                .convert(new ExchangerateRequestDto(from, to, amount))
                .map(api -> new ExchangerateResponseDto(
                        api.success(),
                        api.query(),
                        api.result()
                ))
                .map(ResponseEntity::ok);
    }


}
