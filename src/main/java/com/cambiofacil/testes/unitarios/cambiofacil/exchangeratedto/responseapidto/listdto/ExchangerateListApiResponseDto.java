package com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.responseapidto.listdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangerateListApiResponseDto(
        boolean success,
        String terms,
        String privacy,
        Map<String, String> currencies
) {
}
/*(coipar isso :
 * anotar a class com @JsonIgnoreProperties(ignoreUnknown = true) para o
 * jackson ignorar demais dados que nao estao mapeadas
 * bolean success,
 * String terms,
 * String privacy,
 * Map<String, String> Currencies*/