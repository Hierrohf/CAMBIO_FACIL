package com.cambiofacil.testes.unitarios.cambiofacil.dto.response;

import java.util.Map;

public record ExchangerateResponseDto(
        Map<String, String> query,
        String result//ou Long, tenho que ver isso dps kkkk
) {
}
