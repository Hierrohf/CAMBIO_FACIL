package com.cambiofacil.testes.unitarios.cambiofacil.config.exchangerapi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ConfigurationProperties(prefix = "api.exchangerate")
public class ExchangerateApiProperties {

    private String key;
    private String baseUrl;

}
/*crir uma class de propriedades para a api que iremos consumir (Exchanger)
* a class deve se chamar ExchangerApiProperties
* devemos passar 2 propriedades do tipo string
* 1 key = a chave da api
* 2 baseUrl = a url base para assecar a api
* agora devemos anotala com @Setter da biblioteca lombok pois o spring precisa setar os valores
*
* breve esplicacao sobre o @ConfigurationProperties(prefix = "api.exchanger")
* essa anotation diz pro spring mapeia as configuracoes para um objeto java
* com iso utilizamos o binding estruturado e nao a Injeção pontual
* ### DIFERENCA ENTRE binding estruturado e Injeção pontual ###
*
*       injecao pontual e quando voce puxa um valor isolado e fora de qualquer contexto estrutural
*       ex: @Value("${api.exchanger.key}")
*           private String key;
*
*       ###binding estruturado###
*       a diferenca e que voce nao pega valores soltos, vc define a estrutura, e o spring preenche essa estrutura inteira
*       que e esatamente esse o proposito dessa class que estamos criando
*
* ###prefix = "api.exchanger###
* isso diz ao spring tudo o que estiver abaixo de api.exchanger no .properties ou no y pega e seta os valores dessa class
*
*    */
/*ainda nao utilizei essa class no WebCLientConfig e nem no ExchangerateService*/