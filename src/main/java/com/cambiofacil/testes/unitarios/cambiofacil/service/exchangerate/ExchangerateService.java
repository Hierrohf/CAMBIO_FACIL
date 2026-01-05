package com.cambiofacil.testes.unitarios.cambiofacil.service.exchangerate;

import com.cambiofacil.testes.unitarios.cambiofacil.config.exchangerapi.ExchangerateApiProperties;
import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.responseapidto.listdto.ExchangerateListApiResponseDto;
import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.request.ExchangerateRequestDto;
import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.responseapidto.convertdto.ExchangerateConvertApiResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExchangerateService {

    private final WebClient webClient;
    private final ExchangerateApiProperties properties;

    public ExchangerateService(WebClient webClient, ExchangerateApiProperties properties){
        this.webClient = webClient;
        this.properties = properties;
    }

    public Mono<ExchangerateConvertApiResponseDto> exchangerConvert(ExchangerateRequestDto requestDto){
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/convert")
                                .queryParam("access_key", properties.getKey())
                                .queryParam("from", requestDto.from())
                                .queryParam("to", requestDto.to())
                                .queryParam("amount", requestDto.amount())
                                .build())
                .retrieve()
                .bodyToMono(ExchangerateConvertApiResponseDto.class)
                .doOnNext(System.out::println);
    }

    public Mono<ExchangerateListApiResponseDto> exchangerList(){
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/list")
                                .queryParam("access_key", properties.getKey())
                                .build())
                .retrieve()
                .bodyToMono(ExchangerateListApiResponseDto.class)
                .doOnNext(System.out::println);
    }
    /*vamos criar o metodo (list) para retornar a lista de moedas disponiveis
    * primeiro vamos criar um metodo publico que ira retornar um Mono<ExchangerateListApiResponseDto>
    * criar um dto com o nome (ExchangerateListApiResponseDto) que sera usado pelo jackson
    * (coipar isso :
    * anotar a class com @JsonIgnoreProperties(ignoreUnknown = true) para o jackson ignorar demais dados que nao estao mapeadas
     * bolean success,
    * String terms,
    * String privacy,
    * Currencies currencies,
    *
    * Currencies : map<String, String> currencies
    * )
    *
    * o metodo deve se chamar exchangerList nao recebera nd nos parametos do metodo
    * agora vamos montar o retorno devolvendo um webClient que e nosso objeto de instancia do WebClient
    * com ele vamos chamar o metodo .get() para indicarmos qual e o tipo de metodo da requisicao
    * com o metodo get vamos montar a uri par ser desparada na request ent .uri(uriBuilder -> )
    * dentro do metodo ele nos da um objeto do tipo UriBuilder par construirmos a uri
    * vamos usar o metodo path("") que serve para passar o caminho\ localizacao especifica de um recurso
    * neste caso passamos no parametro path("/list")
    * em seguida vamos definir os parametro da uri
    * .queryParam("",) aqui vamos passar a chave da api
    * ent .queryParam("access_key",chave no properties)
    * em seguida finalizamos pois n precisamos mandar mais nd
    * .build()
    * agora que montamos a requisicao vamos disparala com .retrieve()
    * esse metodo nao retorna a resposta da api externa mais sim um objeto ResponseSpec que e basicamente :
    * retrieve : "fiz a requisisao Http. agora me diga como voce quer lidar com a resposta"
    * ent agora usamos o metodo bodyToMono(.class) com esse metodo falamos pro retrieve() transforma a resposta nessa class aqui
    * agora va para o controller ...*/
}