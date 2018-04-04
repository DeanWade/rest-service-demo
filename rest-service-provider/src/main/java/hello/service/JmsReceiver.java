package hello.service;

import hello.domain.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Profile("jms")
@Component
public class JmsReceiver {

    @JmsListener(destination = "order.transaction", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(Order order) {
        try {
            Thread.sleep(order.getAmount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
