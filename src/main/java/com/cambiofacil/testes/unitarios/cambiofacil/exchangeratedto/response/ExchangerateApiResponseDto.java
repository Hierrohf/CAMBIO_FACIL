package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangerateApiResponseDto(
        Boolean success,
        Query query,
        Info info,
        BigDecimal result
) {
}
