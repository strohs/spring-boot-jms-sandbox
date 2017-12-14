package com.cliff.jms;

import com.cliff.jms.domain.Confirmation;
import com.cliff.jms.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Uses @JmsListener to consume messages from the user queue, and then uses the ConfirmationSender class to
 * publish confirmation messages to the confirmation queue
 * User: Cliff
 */
@Component
public class UserReceiver {

    private Logger logger = LoggerFactory.getLogger( UserReceiver.class );
    private static AtomicInteger id = new AtomicInteger( );

    @Autowired
    private ConfirmationSender confirmationSender;

    /**
     * automatically builds a DefaultMessageListenerContainer on the 'userQueue'. Also automatically converts
     * javax.jms.Message to a User object
     * @param receivedUser - User object converted from jmx message object
     * @param message - the original jmx message on the queue
     */
    @JmsListener( destination = "userQueue", containerFactory = "myFactory")
    public void receiveMessage( User receivedUser, Message message ) {
        logger.info( " >>>>>>>>>> Original received message: " + message );
        logger.info( " >>>>>>>>>> Received user: " + receivedUser );

        confirmationSender.sendMessage( new Confirmation( id.incrementAndGet(),
                "User " + receivedUser.getLastName() + " received" ) );

    }
}
