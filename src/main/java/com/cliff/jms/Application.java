package com.cliff.jms;

import com.cliff.jms.domain.User;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@SpringBootApplication
@EnableJms
public class Application {

    private static Logger logger = LoggerFactory.getLogger( Application.class );


	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run( Application.class, args);

        JmsTemplate jmsTemplate = ctx.getBean( JmsTemplate.class );


        logger.info("Sending a user message.");
        jmsTemplate.convertAndSend( "userQueue", new User( "Randy","Jones",99) );

        logger.info("Waiting for user and confirmation...");
        System.in.read();
        ctx.close();

	}

    @Bean("jacksonConverter")
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType( MessageType.TEXT );
        converter.setTypeIdPropertyName( "_type" );
        return converter;
    }

//    @Bean
//    public MessageConverter simpleMessageConverter() {
//        return new SimpleMessageConverter();
//    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory( ConnectionFactory connectionFactory,
                                                     DefaultJmsListenerContainerFactoryConfigurer configurer ) {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    // This provides all of boot's defaults to this factory, including the message converter
	    configurer.configure( factory, connectionFactory );
	    factory.setMessageConverter(  jacksonJmsMessageConverter() );
	    return factory;
    }

    /**
     * this used when connecting to a standalone/remote broker
     * 
     */
//    @Bean ConnectionFactory nativeConnectionFactory(){
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
//        connectionFactory.setBrokerURL("tcp://localhost:61616");
//        connectionFactory.setTrustAllPackages( true );
//        return connectionFactory;
//    }

    @Bean
    Queue userQueue(){
        return new ActiveMQQueue("queues.users");
    }

    @Bean
    Queue confirmationQueue(){
        return new ActiveMQQueue("queues.confirmation");
    }
}
