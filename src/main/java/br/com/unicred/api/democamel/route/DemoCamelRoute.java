package br.com.unicred.api.democamel.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.unicred.api.democamel.aggregation.AggregateFileLines;
import br.com.unicred.api.democamel.predicate.ExtensaoArquivoValida;
import br.com.unicred.api.democamel.predicate.TamanhoArquivoValido;
import br.com.unicred.api.democamel.processor.ArquivoComConteudoProcessor;
import br.com.unicred.api.democamel.processor.ExtensaoValidaProcessor;

@Component
public class DemoCamelRoute extends RouteBuilder {

    private static final String WRAP_LINE_REGEX = "\\r\\n|\\n|\\r";

    @Autowired
    private ExtensaoArquivoValida extensaoArquivoValida;

    @Autowired
    private TamanhoArquivoValido tamanhoArquivoValido;

    @Autowired
    private ExtensaoValidaProcessor extensaoProcessor;

    @Autowired
    private ArquivoComConteudoProcessor conteudoProcessor;
    
    @Autowired
    private AggregateFileLines aggregateFileLines;

    @Override
    public void configure() throws Exception {

        //        from(endpointScheduled)
        //            .routeId(scheduledId)
        //            .log("Started scheduled route for testing.")
        //            .to(endpointScheduledRedir)
        //            .log("Logging performed.");
        /*
         * 1 - recebe o arquivo
         * 2 - envia para rotas de validação
         *  -> usar um pipeline *sequencial(rotaValidarExtensaoArquivo,rotaValidarTamanhoArquivo)
         *  -> usar um multicast *paralelo(rotaValidarExtensaoArquivo,rotaValidarTamanhoArquivo)
         *  
         * 3 - 
         */

        from("{{app.routes.recebidos}}")
            .routeId("{{app.routes-id.recebidos}}")
            .log(LoggingLevel.INFO, "{{app.messages.log-welcome}}")
            .choice()
                .when(extensaoArquivoValida)
                    .log("Validada extensao de arquivo")
                    .process(extensaoProcessor)
                    .to("{{app.routes.codificacao-arquivo}}")
                .otherwise()
                    .log(LoggingLevel.WARN, "Extensao de arquivo invalida")
                    .to("{{app.routes.invalidos}}")
            .end();

        from("{{app.routes.codificacao-arquivo}}")
            .routeId("{{app.routes-id.codificacao-arquivo}}")
            .choice()
                .when(tamanhoArquivoValido)
                    .log("Validado tamanho do arquivo")
                    .process(conteudoProcessor)
                    .to("{{app.routes.validos}}")
                .otherwise()
                    .log("Tamanho de arquivo invalido")
                    .to("{{app.routes.invalidos}}")
            .end();

        from("{{app.routes.validos}}")
            .routeId("{{app.routes-id.validos}}")
            .split(body().regexTokenize("\\r\\n|\\n|\\r"), aggregateFileLines)
            .choice()
                .when(simple("${property.CamelSplitIndex} > 0"))
                    .to("{{app.routes.line}}")
                .otherwise()
                    .to("{{app.routes.header}}")
            .endChoice()
            .to("{{app.routes.relatorio}}")
        .end();

    }

}
