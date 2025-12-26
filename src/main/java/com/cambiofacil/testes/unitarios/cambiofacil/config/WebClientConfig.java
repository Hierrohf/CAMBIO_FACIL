package com.cambiofacil.testes.unitarios.cambiofacil.config;

import com.cambiofacil.testes.unitarios.cambiofacil.config.exchangerapi.ExchangerateApiProperties;
import com.cambiofacil.testes.unitarios.cambiofacil.dto.response.ExchangerateResponseDto;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

//    @Value("${api.exchangerate.base-url}")
//    private String baseUrlExchangerate;

//    @Value("${api.exchanger.key}")
//    private String apikeyExchangerate;

    private final ExchangerateApiProperties properties;

    public WebClientConfig(ExchangerateApiProperties properties){
        this.properties = properties;
    }

    @Bean
    public WebClient webClientExchangerate(WebClient.Builder builder) {

//        HttpClient httpClient = HttpClient.create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
//                .responseTimeout(Duration.ofSeconds(3))
//                .doOnConnected(connection ->
//                        connection.addHandlerLast(new ReadTimeoutHandler(3))
//                                .addHandlerLast(new WriteTimeoutHandler(3))
//                                .bind()
//                );

        return builder.clientConnector(new ReactorClientHttpConnector(httpClientExchangerate()))
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                //.defaultHeader("access_key", apikeyExchangerate) a api que vomos interagir n le a key no header mas sim no query param(ou seja ela le na uri)
                .build();
    }

    /*depois de definir as variaveis de ambiente no .properties vamos para essa class construir o webClient pra que possamos
    * interagir com uma api externa
    vamos criar a isntancia ja configurada de WebClient
    * o metodo vai ser uma configuracao ent anotamos este metodo com @Bean*
    o metodo deve ser publico e retornar um WebClient o nome do metodo deve ser webClientExchangerate
    ele deve receber em seu parametro um WebClient.Builder que ira servir para pasarmos config para o WebClient
    * tbm devera receber uma instancia de ExchangerApiProperties que e nossa class de propriedades de conexao da api que
    * iremos interagir
    * ja vamos montar o retorno
    * utilizamos o Builder para passar algumas cofigs
    * primeiro vamos passar o HttpClient para isso utilizamos o metodo .clientConnector() pasando em seu parametro uma instancia
    * de ReactorClientHttpConnector que e como um conversor que permite o HttpClient e o WebClient se comunicarem
    * em seguida passamos a url base com o metodo .baseUrl() ja nos seus parametros utilizamos a instancia de ExchangerApiProperties
    * camando o metodo GetUrl()
    * em seguida vamos passar as configs de tipo de dado que iremos enviar e receber
    * para isso utilizamos o .defaultHeadesr(HttpHeaders, MediaType)
    * e por fim vamos dar um .build()
     */


    private HttpClient httpClientExchangerate() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .responseTimeout(Duration.ofSeconds(3))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new WriteTimeoutHandler(3))
                                .addHandlerLast(new ReadTimeoutHandler(3))
                                .bind()
                );
    }
    /*criar um metodo privado que devolva um HttpClient
     * o metodo deve se chamar httpClientExchangerate e recebe em seu parametro um HttpClient
     * com o metodo .create podemos passar configuracoes
     * utilizando o metodo .option() ele te permite passar config a nivel de rede
     * nos parametros deste metodo podemos passar o timeout de conexao que e o tempo que demora para estabelecer a conexao
     * para isso chamamos a class ChannelOption que e responsavel pelo canal de conexao (soket/tcp)
     * com essa class podemos configurar o tempo : .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
     * apos passarmos o metodo option estamos configurando a nivel de rede
     * agora vamos passar o tempo da resposta para isso utilizamos o metodo
     * .responseTimeout() dentro desse metodo podemos passar um metodo static
     * Duration.ofSeconds(s)
     * agora vamos passar configuracoes depois que a soket/conexao e estabelecido
     * pois alguns controladores so podem ser implementado apos a conexao for estabelecida
     * para isso usamos o metodo
     * .doOnConnected(var -> )
     * com esse metodo ele nos permite passar handlers\controladores pra o ChannelPipeline que e onde fica a lista de
     * handlers
     * os handlers que presisamos passar sao apos a conexao ent usamos o
     * .addHandlerLast() e em seu parametro pode se passar uma instancia de uma calss
     * como o ReadTimeoutHandler() que espera em seu parametro uma duracao em segundos
     * tbm podemos passar outa class par definir o tempo de escrita da resposta da api */
}

/*CONEXAO TCP: é um tipo de comunicação confiável e orientada a conexão na internet,
    estabelecida através de um processo chamado "handshake de três vias" antes da troca de dados.
    O Protocolo de Controle de Transmissão (TCP) garante a entrega ordenada e segura dos pacotes de dados,
    reorganizando-os no destino e solicitando retransmissões em caso de perdas.*/


/*  #####.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)#####

    Isso não é do WebClient.
    É do Netty, que é o motor (engine) de rede que o WebClient usa quando está no modo reativo.

    Aqui você está mexendo no canal TCP, antes mesmo de existir uma requisição HTTP.

    Esse timeout significa:
    “Quanto tempo eu espero para conseguir CONECTAR no servidor?”

    Ou seja:
    Se a conexão nem abriu, o request nem começou.
    Por isso o nome CONNECT_TIMEOUT_MILLIS vem do Netty, não do WebClient.

    Ele é passado dentro de .option(...) porque .option configura opções de baixo nível da conexão, específicas de Netty.


    #####.responseTimeout(Duration.ofSeconds(3))#####

Agora você já está mexendo em comportamento do WebClient.

Esse timeout significa:
“Depois que eu enviei a requisição, quanto tempo eu espero pela resposta?”

Ou seja:

Conectou → mandou request → tá esperando o servidor responder → se demorar mais do que isso, estoura.

O nome é responseTimeout porque isso é uma configuração de tempo de resposta HTTP, não de conexão TCP.


--------------------------------------------------------------------------------------------------------
Por que eles têm nomes diferentes?

Porque:

CONNECT_TIMEOUT_MILLIS → pertence ao Netty → lida com conexão física.

responseTimeout(...) → pertence ao WebClient → lida com tempo de resposta da requisição HTTP.

Eles não são a mesma camada da aplicação, então não teriam nomes iguais mesmo.*/

    /*### O ChannelOption ###é o que controla o comportamento do socket, antes mesmo de começar o HTTP.

    Ele controla coisas como:

    Tempo de conexão

    Tamanho do buffer

    Keep-alive no TCP

    Reutilização de endereço

    etc.*/

    /*O que é doOnConnected NA ESSÊNCIA

        É um callback (um gancho) do Reactor Netty que executa exatamente quando a conexão TCP foi estabelecida.

        Ou seja:
        A conexão abriu ✔
        O handshake TCP rolou ✔
        Agora você tem um canal (Channel) conectado ✔
        E antes de qualquer request HTTP começar,
        o Netty te dá acesso ao Connection.

        E aí você pode modificar o pipeline do Netty, adicionando handlers de baixo nível.
    A pipeline existe ANTES de você fazer o request HTTP
    Mas certos handlers só podem ser colocados depois que o canal se conecta.

Por isso existe:
        .doOnConnected(conn ->
    conn.metodo()
        .metodo()
)

        */