package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.responseapidto.convertdto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangerateConvertApiResponseDto(
        Boolean success,
        Query query,
        Info info,
        BigDecimal result
) {
}
