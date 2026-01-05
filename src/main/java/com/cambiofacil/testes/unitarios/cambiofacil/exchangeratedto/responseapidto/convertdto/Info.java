package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.responseapidto.convertdto;

import java.math.BigDecimal;

public record Info(
        Long timestamp,
        BigDecimal quote
) {
}
