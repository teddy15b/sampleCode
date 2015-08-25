package pubsub;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogs {

  private static final String EXCHANGE_NAME = "average";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

//    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true, true, null);
    String queueName = channel.queueDeclare().getQueue();
    System.out.println(queueName);
    channel.queueBind(queueName, EXCHANGE_NAME, "");
    
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(queueName, true, consumer);

    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());

      System.out.println(" [x] Received '" + message + "'");   
    }
  }
}