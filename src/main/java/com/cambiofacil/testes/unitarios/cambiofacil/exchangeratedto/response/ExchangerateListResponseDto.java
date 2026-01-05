package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response;


import java.util.Map;

public record ExchangerateListResponseDto(
        boolean success,
        Map<String, String> currencies
) {
}
/*copiar isso: (ExchangerateListResponseDto :
 * bolean success,
 * Currencies currencies)*/