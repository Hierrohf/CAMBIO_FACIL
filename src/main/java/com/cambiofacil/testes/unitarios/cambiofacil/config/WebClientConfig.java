package com.cambiofacil.testes.unitarios.cambiofacil.config;

import com.cambiofacil.testes.unitarios.cambiofacil.config.exchangerapi.ExchangerateApiProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(ExchangerateApiProperties.class)
public class WebClientConfig {

    private final ExchangerateApiProperties properties;

    public WebClientConfig(ExchangerateApiProperties properties){
        this.properties = properties;
    }

    @Bean
    public WebClient webClientExchangerate(WebClient.Builder builder) {
        return builder.clientConnector(new ReactorClientHttpConnector(httpClientExchangerate()))
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

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