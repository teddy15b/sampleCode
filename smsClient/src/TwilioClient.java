import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;

// Install the Java helper library from twilio.com/docs/java/install

 
public class TwilioClient { 
 
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "need to apply";
  public static final String AUTH_TOKEN = "need to apply";
 
  public void send() throws TwilioRestException {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
 
    // Build a filter for the SmsList
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "All in the game, yo"));
    params.add(new BasicNameValuePair("To", "+xxxxx"));
    params.add(new BasicNameValuePair("From", "+xxxxxx"));
     
     
    SmsFactory smsFactory = client.getAccount().getSmsFactory();
    Sms sms = smsFactory.create(params);
    System.out.println(sms.getSid());
  }
}