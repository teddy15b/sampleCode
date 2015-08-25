package pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

  private static final String EXCHANGE_NAME = "sensing";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

//    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true, true, null);
    channel.exchangeDeclare("average", "fanout", true, true, null);
    
    channel.queueDeclare("sample-queue", true, false, false, null);
    channel.queueBind("sample-queue", EXCHANGE_NAME, "");    
    channel.queueBind("sample-queue", "average", "");
    
//
//    String message = getMessage(argv);
    
    ObjectMapper objectMapper = new ObjectMapper();
    String message = objectMapper.writeValueAsString(new TestObject("123","456"));

    channel.basicPublish(EXCHANGE_NAME, "sample-queue", null, message.getBytes());
    channel.basicPublish("average", "sample-queue", null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
  
  private static String getMessage(String[] strings){
    if (strings.length < 1)
            return "info: Hello World!";
    return joinStrings(strings, " ");
  }
  
  private static String joinStrings(String[] strings, String delimiter) {
    int length = strings.length;
    if (length == 0) return "";
    StringBuilder words = new StringBuilder(strings[0]);
    for (int i = 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }
}