package misc;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.joda.time.DateTime;


public class Test {

  

  public static void main(String[] args) {
    OffsetDateTime offsetDateTime =
        Instant.ofEpochMilli(1475251800000L).atOffset(
            ZoneOffset.UTC);
    OffsetDateTime createdTime = offsetDateTime.minusMinutes(10);
    OffsetDateTime representTime = offsetDateTime.minusDays(offsetDateTime.getDayOfMonth()).minusMinutes(10);
    System.out.println(createdTime.toString());
    System.out.println(representTime.toString());
    DateTime now = new DateTime();
    System.out.println(now.toDate());
    
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
//    DateTimeFormatter formatter = DateTimeFormatter.OfPattern("yyyy-M-d H:m:s");
//    DateTime dateTime = formatter.parseDateTime(at.substring(0, 19));
//    System.out.println(dateTime.toString());
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");
//    LocalDateTime dateTime = LocalDateTime.parse(at.substring(0, 19), formatter);   
//    System.out.println(dateTime.toString());
    
//    ZonedDateTime date = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Taipei"));
//    DateTime dt = new DateTime(theDate);    
//    System.out.println(dt.toString());
//    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-M-d'T'H:m:sz");
//    ZonedDateTime date1 = ZonedDateTime.parse(dateTime.toString());
//    System.out.println(date1.getZone());

    return null;
  }

}
