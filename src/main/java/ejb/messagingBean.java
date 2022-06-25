package ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.MessageDriven;
import jakarta.faces.context.FacesContext;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;

@MessageDriven(
        mappedName = "jms/DestinationResource"
)
public class messagingBean implements MessageListener {
    @Resource(
            lookup = "jms/DADemo/ConnectionFactory"
    )
    private ConnectionFactory connectionFactory;
    @Resource(
            lookup = "jms/DestinationResource"
    )
    private Queue queue;
    private FacesContext context;

    public messagingBean() {
    }

    public void onMessage(Message message) {
        try {
            System.out.println("Print Message received that a " + (String)message.getBody(String.class));
        } catch (Exception e) {
            System.out.println("Print " + e);
        }

    }

    public void sendPrintingMessage(String message) throws JMSException {
        try {
            Context ctx = new InitialContext();
            ConnectionFactory connectionFactory1 = (ConnectionFactory)ctx.lookup("jms/DADemo/ConnectionFactory");
            JMSContext context = connectionFactory1.createContext();
            Destination queue = (Destination)ctx.lookup("jms/DestinationResource");
            context.createProducer().send(queue, message);
            System.out.println("Print Message has been send");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
