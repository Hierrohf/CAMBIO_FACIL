package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response;

import java.math.BigDecimal;

public record ExchangerateResponseDto(
        boolean success,
        Query query,
        BigDecimal result
) {
}
