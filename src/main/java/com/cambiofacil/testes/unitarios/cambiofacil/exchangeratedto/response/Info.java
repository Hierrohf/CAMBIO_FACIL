package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response;

import java.math.BigDecimal;

public record Info(
        Long timestamp,
        BigDecimal quote
) {
}
