package pubsub;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLogTopic {

  private static final String EXCHANGE_NAME = "cwms";

  public static void main(String[] argv) {
    Connection connection = null;
    Channel channel = null;
    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
      factory.setUsername("dap");
      factory.setPassword("dapisno1");
  
      connection = factory.newConnection();
      channel = connection.createChannel();

      channel.exchangeDeclare(EXCHANGE_NAME, "topic", true, true, null);

//      String routingKey = getRouting(argv);
      String routingKey = "sensor.data.any.avg.5min";
      String message = "{\"path\":\"/feeds/540ff57ae4b0c6fadc38fe7c/channels/540ff57ae4b0c6fadc38fe81/data/insert\",\"method\":\"post\",\"header\":{\"Content-Type\":\"application/json; charset=UTF-8\"},\"body\":\"{\"kind\":\"insertDataRequest\",\"payload\":{\"resources\":[{\"value\":{\"Temperature\":\"34.06\",\"dataCode\":\"10\"},\"at\":\"2014-09-10T08:36:50.002Z\"}]}}\"}";

      channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
      System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

    }
    catch  (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (connection != null) {
        try {
          connection.close();
        }
        catch (Exception ignore) {}
      }
    }
  }
  
  private static String getRouting(String[] strings){
    if (strings.length < 1)
            return "anonymous.info";
    return strings[0];
  }

  private static String getMessage(String[] strings){ 
    if (strings.length < 2)
            return "Hello World!";
    return joinStrings(strings, " ", 1);
  }
  
  private static String joinStrings(String[] strings, String delimiter, int startIndex) {
    int length = strings.length;
    if (length == 0 ) return "";
    if (length < startIndex ) return "";
    StringBuilder words = new StringBuilder(strings[startIndex]);
    for (int i = startIndex + 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }
}