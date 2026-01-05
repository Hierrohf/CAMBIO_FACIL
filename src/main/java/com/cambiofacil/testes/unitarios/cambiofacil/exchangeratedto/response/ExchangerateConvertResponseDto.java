package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response;

import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.responseapidto.convertdto.Query;

import java.math.BigDecimal;

public record ExchangerateConvertResponseDto(
        boolean success,
        Query query,
        BigDecimal result
) {
}
