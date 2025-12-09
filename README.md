CambioFácil — Conversor de Moedas

Aplicação construída para explorar Spring WebFlux, Reactor Netty, WebClient, HttpClient, timeouts, tratamento de erros, boas práticas e consumo de API externa.
Não é um produto comercial — é um laboratório de aprendizado, focado em domínio técnico e arquitetura limpa (mesmo que, por enquanto, cheia de anotações e experimentações).

🚀 Objetivo do Projeto

Este projeto existe com um propósito claro:

Aprofundar o domínio da stack reativa do Spring, entender chamadas externas em pipelines não-bloqueantes, manipular timeouts em níveis diferentes e lidar com falhas de rede de forma madura.

Ele não pretende ser um sistema finalizado ou um conversor comercial.
Ele é um ambiente controlado onde eu posso:

testar decisões,

quebrar o código,

ajustar,

observar o comportamento interno das ferramentas,

e evoluir conforme meu entendimento aumenta.

🧱 Tecnologias Utilizadas
🔧 Backend

Java 21

Spring Boot

Spring WebFlux

WebClient

HttpClient (Reactor Netty)

Project Reactor

Maven

⚙️ Infra / Configurações

Variáveis de ambiente

application.properties externalizado

Configuração manual do clientConnector (ReactorClientHttpConnector)

Pipeline customizado com handlers de timeout

Desabilitação explícita de:

DataSourceAutoConfiguration

HibernateJpaAutoConfiguration
(o projeto não usa banco, então o contexto fica mais leve e rápido)

🔄 Evolução Contínua do Projeto

Este projeto, Ele progride junto com meu entendimento das tecnologias.
A medida que aprofundo novos conceitos, o código cresce, muda e se refatora.
