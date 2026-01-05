package com.cambiofacil.testes.unitarios.cambiofacil.controller;

import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.request.ExchangerateRequestDto;
import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response.ExchangerateConvertResponseDto;
import com.cambiofacil.testes.unitarios.cambiofacil.exchangeratedto.response.ExchangerateListResponseDto;
import com.cambiofacil.testes.unitarios.cambiofacil.service.exchangerate.ExchangerateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/exchangerate")
public class ExchangerateController {

    private final ExchangerateService service;

    public ExchangerateController(ExchangerateService service){
        this.service = service;
    }

    @GetMapping("/convert")
    public Mono<ResponseEntity<ExchangerateConvertResponseDto>> exchangerateConvert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount
    ) {
        return service
                .exchangerConvert(new ExchangerateRequestDto(from, to, amount))
                .map(api -> new ExchangerateConvertResponseDto(
                        api.success(),
                        api.query(),
                        api.result()
                ))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<ExchangerateListResponseDto>> exchangerList(){
        return service.exchangerList()
                .map(api -> new ExchangerateListResponseDto(
                        api.success(),
                        api.currencies()
                ))
                .map(ResponseEntity::ok);
    }
    /*criar um endPoint que para o metodo exchangerList que retorna uma lista de moedas disponiveis
    * o metodo deve ser anotado com @GetMapping(/list)
    * metodo publico que reotorna um Mono<ResponseEntity<ExchangerateListResponseDto>>
    * copiar isso: (ExchangerateListResponseDto :
    * bolean success,
    * Map<String, String> Currencies)
    * o nome do metodo sera exchangerList nao recebe nd nos parametro
    * montando o retorno usamos a class service para chamar o metodo
    * em seguida usamos o metodo .map para mapear o retorno para o dto que iremos devolver para o client
    * ent o medoto exchangerList(da class service) retorna um (ExchangerateListApiResponseDto) que e a resposta da api
    * externa porem vamos trasnformala em uma resposta para o cliente que seria o (ExchangerateListResponseDto)
    * ent usamos o metodo .map(var ->)
    * com isso vamos instanciar o dto de resposta para o client (ExchangerateListResponseDto)
    * usando a var do metodo map vamos passar os valores var.success e currencies
    * agora vamos pegar o que o map retorna e embrulhar em um ResponseEntity devolvendo o codigo 200ok
    * e necessario isso pois meu metodo esta assinado assim: Mono<ResponseEntity<ExchangerateListResponseDto>>
    ent eu preciso devolver o status nao apenas o body */
}
