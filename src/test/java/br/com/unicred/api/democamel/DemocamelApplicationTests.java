package br.com.unicred.api.democamel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import br.com.unicred.api.democamel.route.DemoCamelRoute;

@RunWith(CamelSpringRunner.class)
public class DemocamelApplicationTests extends CamelTestSupport {

    @Autowired
    private CamelContext context;

    @Autowired
    private Environment environment;

    @Autowired
    private ProducerTemplate producerTemplate;
    
    @InjectMocks
    private DemoCamelRoute demoRoute;

    @Test
    public void contextLoads() {
 
    }

}
