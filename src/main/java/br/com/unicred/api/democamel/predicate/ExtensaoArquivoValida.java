package br.com.unicred.api.democamel.predicate;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

@Component
public class ExtensaoArquivoValida implements Predicate {

    @Override
    public boolean matches(Exchange exchange) {
        return exchange.getMessage().getHeader(Exchange.FILE_NAME).toString().endsWith("RET");
    }

}
