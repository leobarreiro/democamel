package br.com.unicred.api.democamel.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ArquivoComConteudoProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        LocalDateTime ldt = LocalDateTime.now();
        exchange.getIn().setHeader("CONTEUDO_NAO_NULO", ldt);
    }

}
