package com.cambiofacil.testes.unitarios.cambiofacil.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record ExchangerateRequestDto(

        @NotBlank
        @Size(min = 3, max = 3)
        String from,

        @NotBlank
        @Size(min = 3, max = 3)
        String to,

        @NotNull
        Long amount
) {
}
