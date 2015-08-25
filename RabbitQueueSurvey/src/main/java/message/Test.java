package message;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;



public class Test {

  

  public static void main(String[] args) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("1", "test");
    map.forEach((key, value) -> {System.out.println(key+","+value);});
    map.clear();
    map.forEach((key, value) -> {System.out.println(key+","+value);});
    String tmp = "2014-09-22 00:38:15+08:00";
    //ZonedDateTime date = convert1(tmp); 
    //ZonedDateTime date = ZonedDateTime.parse("2014-10-10T00:49:00+08:00");
    OffsetDateTime at = OffsetDateTime.parse("2015-07-06T16:00:00+08:00");
    System.out.println(at.toString());
  }
  
  private static ZonedDateTime convert1(String at) {
//    Date theDate = null;
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:m:s");
//    sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//    try {
//      theDate = sdf.parse(at.substring(0, 19));
//    } catch (ParseException e) {
//
//    }
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-M-d H:m:s");
    DateTime dateTime = formatter.parseDateTime(at.substring(0, 19));
    System.out.println(dateTime.toString());
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");
//    LocalDateTime dateTime = LocalDateTime.parse(at.substring(0, 19), formatter);   
//    System.out.println(dateTime.toString());
    
//    ZonedDateTime date = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Taipei"));
//    DateTime dt = new DateTime(theDate);    
//    System.out.println(dt.toString());
//    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-M-d'T'H:m:sz");
    ZonedDateTime date1 = ZonedDateTime.parse(dateTime.toString());
    System.out.println(date1.getZone());

    return null;
  }

}
