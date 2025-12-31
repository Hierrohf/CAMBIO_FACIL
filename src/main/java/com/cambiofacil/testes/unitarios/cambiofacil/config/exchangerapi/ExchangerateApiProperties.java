package com.cambiofacil.testes.unitarios.cambiofacil.config.exchangerapi;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Setter
@Getter
@Validated
@ConfigurationProperties(prefix = "api.exchangerate")
public class ExchangerateApiProperties {

    @NotBlank
    private String key;

    @NotBlank
    private String baseUrl;

}
