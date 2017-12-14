package com.cliff.jms;

import com.cliff.jms.domain.Confirmation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * User: Cliff
 */
@Component
public class ConfirmationReceiver {

    private Logger logger = LoggerFactory.getLogger( ConfirmationReceiver.class );

    //listens to the confirmationQueue and logs confirmation messages
    @JmsListener( destination = "confirmationQueue", containerFactory = "myFactory" )
    public void receiveConfirmation( Confirmation confirmation ) {
        logger.info(" >>>>>>>>>> Received confirmation: " + confirmation );
    }
}
