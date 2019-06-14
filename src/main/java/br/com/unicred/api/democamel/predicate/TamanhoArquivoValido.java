package br.com.unicred.api.democamel.predicate;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

@Component
public class TamanhoArquivoValido implements Predicate {

    @Override
    public boolean matches(Exchange exchange) {
        return Integer.valueOf(exchange.getMessage().getHeader(Exchange.FILE_LENGTH).toString()) > 0;
    }

}
