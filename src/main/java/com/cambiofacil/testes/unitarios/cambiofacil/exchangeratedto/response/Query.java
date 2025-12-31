package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response;

import java.math.BigDecimal;

public record Query(
        String from,
        String to,
        BigDecimal amount
) {
}
