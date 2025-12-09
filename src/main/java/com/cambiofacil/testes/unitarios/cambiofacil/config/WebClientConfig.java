package com.cambiofacil.testes.unitarios.cambiofacil.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

public class WebClientConfig {

    /*criar atributos string : baseUrlExchangerate e ApikeyExchangerate
    * anotar com @value e passamos a variavel de ambiente que foi configurada no
    * .properties :api.exchangerate.base-url e api.exchanger.key
    *
    * criar metodo private que devolve um Web client chamado webClientExchangerate
    * metodo deve ser anotado com @Bean
    * nos parametros ele recebe uma intancia do webClient.biuld chamada biuld
    * ###
    * instanciar um httpCliet e utilizar o metodo create que permite fazer configuracoes do client
    * agora passamos as config de timeout que precisamos nesse caso
    * O .option() pertence ao HttpClient, que é a camada de rede.
    * nos parametros do option passamos o ChannelOption que controla o socket(é um ponto de extremidade de software que permite a comunicação entre programas)
    *pasando o valor CONNECT_TIMEOUT_MILLIS, (milisegundos)
    *agora que configuramos o tempo maximo para abrir a requisicao devemos determinar o tempo maximo de espera para a
    * api responder
    * para isso passamos o metodo .responseTimeout(passando uma duracao em segundo(Duration.ofSeconds(s)))
    *
    * agora vamos adicionar configuracoes depois que a conexao foi estabelecida vamos utilizar handlers para controlar o
    * tempo de leitura da api externa e o tempo de escrita
    * vamos utilizar o .doOnConnected(var ->
    *       var.HandlerDeLeitura
    *          .HandlerDeEscrita)
    *
    * ###
    * ja devolvendo o retorno: com o return
    * com o objeto biuld podemos passar as configuracoes : (urlBase, defalHeader, chaveApi,
    *   usamos o metodo .ClientConnector(new ReactorClientHttpConnector(objHttpClient))
    *   passar a urlBase(varDeAmbiente)
    *   passar o header de cabecalho(tipo de dado)
    *   passar o header da chave da api(varDeAmbiente)
    *   .build
    * timeOut: Conexão (connect timeout):Tempo para estabelecer a conexão TCP com o servidor. 300ms → 1s
    *          Resposta (read/response timeout):Tempo máximo que você aceita aguardar a resposta depois que a requisição foi enviada. 1s → 3s para APIs rápidas)
    *          Escrita (write timeout):Tempo para enviar o corpo da requisição para o servidor (muito importante em uploads).1s normalmente

            * * [CONNECT_TIMEOUT] → abrir conexão TCP .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                 ↓
            request enviado
                 ↓
            [RESPONSE_TIMEOUT] → servidor precisa começar a responder .responseTimeout(Duration.ofSeconds(3))
                 ↓
            dados sendo recebidos
                 ↓
            [READ_TIMEOUT] → tempo máximo sem receber bytes
                 ↓
            request completo
            * e por fim construir a instancia com .biuld*/

    @Value("${api.exchangerate.base-url}")
    private String baseUrlExchangerate;

    @Value("${api.exchanger.key}")
    private String apikeyExchangerate;

    public WebClient webClientExchangerate(WebClient.Builder builder){

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .responseTimeout(Duration.ofSeconds(3))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(3))
                                .addHandlerLast(new WriteTimeoutHandler(3))
                                .bind()
                        );

        return builder.clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrlExchangerate)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("access_key", apikeyExchangerate)
                .build();
    }

    /*depois de definir as variaveis de ambiente no .properties vamos para essa class construir o webClient pra que possamos
    * interagir com uma api externa
    * primeiro vamos criar 2 atributos (String baseUrlExchangerate, String apikeyExchangerate)
    * que vao representar as urlbase da api que vamos acessar e a chave da api
    *
    * (criar um atributo privado do tipo string chamado baseUrlExchangerate
    * criar um atributo privado do tipo string chamado apikeyExchangerate
    * anotar esse 2 atributos com @value(${variavel.properties}))
    *
    * vamos criar o metodo responsavel por construir parte da nossa requisicao aqui vamos adicionar algumas configuracoes
    * para a connection como tempo de conexao url base a chave da api e dentre outros
    *
    * (metodo private que retorna um webClient chamado webClientExchangerate
    * esse metodo recebe em seus parametros um WebCliente.Builder chamado builder esse .Builder serve para add configuracoes
    * no WebCliente
    * (nao e boa pratica porem para fins de estudo ajuda na mnh comprencao) instanciar um HttpClietn
    * chamado httpClient essa instancia recebe um HttpClient.create() o httpClent vai nos ajudar com config de baixo nivel
    * vamos difinir tempo de conexao antes de conectar e depois ele nos permite definir o tempo para iniciar
    * uma conexao o tempo de resposta tempo de leitura e escrita etc..
    *
    * agora vamos utilizar o metodo .option() para mexer em configuracoes do socket TCP nível de rede, antes do HTTP existir.
    * dentro do optin passamos ChannelOption"opcao de canal" isso permite pasar configuracoes antes msm da requisicao ser
    * feita ele passa configs para o TCP como timeout=tempo para conectar, tamanho dos buffers, se a conexão deve usar keep-alive,
    * se pode reutilizar porta etc
    * nesta caso vamos passar o ennun .CONNECT_TIMEOUT_MILLIS, (milisegundos)
    *  )
    *
    * (agora vamos passar a configuracao do tempo de resposta .responseTimeout(Duration.segundos)
    * agora precisamos definir o tempo que a api vai ter para ler a request e escrever a resposta
    * porem esse tipo de configuracao so pode ser feita depois que a connexao foi estabelecida o ChannelPipeline
    * que e alista de controladores(handler)
    * ent pasamos o metodo .doOnConnected() que nos permite passar Handlers para controlarmos algumas coisas na nossar request
    * a sintaxe do doOnConnected e assim -> .doOnConnected(var ->
                        var.Handler(new )
                                .Handler(new )
                                .bind())
    *
    * o doOnConnected() nos estrega varias opcoes de addHandler o que vamos utilizar aqui e o
    * addHandlerLast() ele permite add um handler depois que a conexao foi estabelecida
    * dentro desse handelr vamos passar uma instancia de uma classe no nosso caso precisamos definir o tempo de leitura
    * ent utilizamos a class ReadTimeoutHandler() nos seus parametros ela recebe um vlr em segundos
    * agora vamos add outro handler importante na nossa channelPipeline que e o de temopo de escrita
    * vamos usar o .addHandlerLast() passando a class WriteTimeoutHandler que tbm recebe vlr em segundos
    * por fim damos um .build pra construirmos o nosso HttpClient que tem a funcao de controlar a conexao
    * É o motor de baixo nível que abre conexões TCP e fala HTTP.
    *
    *
    * agora vamos construir o nosso WebClient que sera responsavel pela requisicao
    * vamos contruir o nosso webclient ja devolvendo o builder configurado
    * ent return builder.
    * agora vamos add algumas config primeiro vou passar as configs do client que tem as cofigs de tempo de conexao
    * que que ja criamos a cima
    * utilizamos o metod .clientConnector() passando uma instancia de ReactorClientHttpConnector(objtHttpCliente) essa class
    * funciona como um adaptador para permitir que o WebClient use o HttpClient do Reactor Netty
    * sem ele o WebClient nao sabe se comunicar com o HttpCliente ent aqui injetamos a Configs do HttpClient no WebClient
    * agora que ja injetamos as cofiguracoes do HttpClient no WebClient vamos add a URL base
    * usamos o metod .urlBase() aqui vamos passar o atributo privado baseUrlExchangerate
    * em seguida vamos add as configs de cobecalho qual o tipo de dado sera enviado ou recebido
    * utilizamos o metod .defaultHeader(passar um httpHeader.contentType, passar um mediaTipe.applicationJsonValue)
    * agora vamos passar a chave ada api utilizamos o metodo
    * .defaultHeader("var na url", atributo privado apikeyExchangerate)
    * agora podemos dar um .build pra finalizarmos o nosso metodo
    * )*/

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