package com.cliff.jms;

import com.cliff.jms.domain.Confirmation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Sends messages to the confirmation queue
 * User: Cliff
 */
@Component
public class ConfirmationSender {

    private static Logger logger = LoggerFactory.getLogger( ConfirmationSender.class );

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage( final Confirmation confirmation ) {
        jmsTemplate.convertAndSend("confirmationQueue", confirmation);
    }
}
