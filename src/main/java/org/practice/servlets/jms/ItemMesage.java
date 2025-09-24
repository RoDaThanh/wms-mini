package org.practice.servlets.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/myQ"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
        }
)
public class ItemMesage implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.err.println("!#!#!#! QUEUE " + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace(System.err);
        }
    }
}
