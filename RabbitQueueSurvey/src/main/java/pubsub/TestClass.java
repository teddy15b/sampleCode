package pubsub;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestClass {

  /**
   * @param args
   */
  public static void main(String[] args) {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection;
    try {
      connection = factory.newConnection();
      Channel channel = connection.createChannel();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}
