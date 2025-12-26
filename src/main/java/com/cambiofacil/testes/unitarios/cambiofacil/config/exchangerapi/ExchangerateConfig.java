package com.cambiofacil.testes.unitarios.cambiofacil.config.exchangerapi;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ExchangerateApiProperties.class)
public class ExchangerateConfig {
}
/*criar uma class que tem a funcao de abilitar as configuracoes para que o
* binding estruturado seja feito
*
* a class deve se chamr ExchangerConfig pois sao as configs especificas
* desta api
* em seguida devemos anotar essa class com @Configuration para informar oa spring que essa e uma class
* de cofiguracoes nao de logica
*
* agora vamos anotar a class com @EnableConfigurationProperties(.class) ela diz ao spring como o binding deve acontecer
* por isso passamos o .class da class de propriedades que criamos
*
* “Crie um bean dessa classe
Faça o binding das propriedades
Gerencie o ciclo de vida dela”
* Sem isso:
não existe bean
não existe binding
não existe injeção */