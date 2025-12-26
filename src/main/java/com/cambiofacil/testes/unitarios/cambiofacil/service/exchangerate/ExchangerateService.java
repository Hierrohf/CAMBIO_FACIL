package com.cambiofacil.testes.unitarios.cambiofacil.service.exchangerate;

import com.cambiofacil.testes.unitarios.cambiofacil.config.exchangerapi.ExchangerateApiProperties;
import com.cambiofacil.testes.unitarios.cambiofacil.dto.request.ExchangerateRequestDto;
import com.cambiofacil.testes.unitarios.cambiofacil.dto.response.ExchangerateResponseDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@Service
public class ExchangerateService {

//    @Value("${api.exchanger.key}")
//    private String apikeyExchangerate;
//
//    @Autowired
//    private WebClient webClient;
//
//    public Mono<ExchangerateResponseDto> convert(ExchangerateRequestDto requestDto){
//        return webClient.method(HttpMethod.GET)
//                .uri(uriBuilder ->
//                        uriBuilder.path("/convert")
//                                .queryParam("access_key",apikeyExchangerate)
//                                .queryParam("from", requestDto.from())
//                                .queryParam("to", requestDto.to())
//                                .queryParam("amount", requestDto.amount())
//                                .build())
//                .retrieve()
//                .bodyToMono(ExchangerateResponseDto.class);
//
//    }

    private final WebClient webClient;
    private final ExchangerateApiProperties properties;

    public ExchangerateService(WebClient webClient, ExchangerateApiProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public Mono<ExchangerateResponseDto> convert(ExchangerateRequestDto requestDto){
        return webClient.method(HttpMethod.GET)
                .uri(uriBuilder ->
                        uriBuilder.path(properties.getBaseUrl())
                                .queryParam("access_key", properties.getKey())
                                .queryParam("from", requestDto.from())
                                .queryParam("to", requestDto.to())
                                .queryParam("amount", requestDto.amount())
                                .build())
                .retrieve()
                .bodyToMono(ExchangerateResponseDto.class);
    }

    /*REFATORACAO:
    * vamos criar um construtor para o WebClient e o ExchangerApiProperties
    *
    * primeiro vamos criar 2 atributos final um do WebClient para que possamos ter um instancia ja com as nossas configs
    * e a segunda um ExchangerApiProperties para que possamos ter acesso a propriedades da api que iremos interagir
    * vamos criar um metodo publico que retornara um objeto do tipo Mono<ExchangerateResponseDto>
    * o metodo deve se chamar convert ele recebera em seu parametro um dto (ExchangerateRequestDto)
    * ja montando o retorno utilizamos o objeto webClient chamando o metodo .method(metodoHttp) ou utilizar o nome do metodo http : .get()
    * agora vamos passar a .uri(var ->)
    * com a var que iremos nomear de uriBuilder
    * vamos utilizar alguns metodos que o UriBuilder nos proporciona como o .path() que serve para montar a parte estrutural da url
    * ##ex: htps://api.exemplo.com/users/42/orders
    *  ( /users/42/orders ) é PATH Isso define o recurso, não filtros nem opções.
    * ##
    * agora podemos passar as .queryParam() que sao os parametros que serao enviados na url os param sao indentificados na url
    * apos o sinal (?) td o conteudo que esta dps dele e um parametro
    * aqui vamos passar a chave da api e os demais parametros necesariao para nosssa api como
    * access_key, from, to, amount a chave da api esta acessivel pelo ExchangerApiProperties.getKey
    * o restante dos dados estao no dto que chegou nos parametros do metodo
    * agora vamos disparar a requisicao com o .retrive() porem esse metodo nao nos retorna o resultado
    * ele indica como sera tratado o resultado
    * portanto utilizamos o metodo bodyToMono(.class) para que seja devolvido um objeto do tipo mono<dtoResposta>
    * */
}

/*  isso a baixo e uma pipeline uma sequencia ordenada de etapas onde cada etapa recebe algo, faz uma transformacao,
* passa a diante, sem executar no final Nada disso executou ainda.
* Você desenhou um caminho.
*
* webClient.method(HttpMethod.GET)
                .uri(uriBuilder ->
                        uriBuilder.path("/convert")
                                .queryParam("access_key",apikeyExchangerate)
                                .queryParam("from", requestDto.from())
                                .queryParam("to", requestDto.to())
                                .queryParam("amount", requestDto.amount())
                                .build())
                .retrieve()
                .bodyToMono(ExchangerateResponseDto.class);
                *
 1 construcao da request :
 * Etapa: request pipeline
    webClient.get()
    .uri(...)

* Metodo HTTP, URI, Headers, Query params. nada foi enviado
* */


/*      ###retrieve()###
* o retrive() nao retorna a resposta HTTP ele retorna um (WebClient.ResponseSpec)
* o ResponseSpec e como se fose um contrato de como voce vai lidar com a resposta nao a resposta em si
* Nada foi lido ainda.
* Nada foi convertido ainda.
* Nada foi executado ainda.
* Você está dizendo: “quando a resposta vier, faça isso com ela”*/