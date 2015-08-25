import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class SmsgoClient {

  public String send() throws Exception {

    // httpclient 4.3.4 ignoring ssl certificate
//    SSLContextBuilder builder = new SSLContextBuilder();
//    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
//    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
//    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

    CloseableHttpClient httpclient = HttpClients.createDefault();
    URI uri =
        new URIBuilder().setScheme("https").setHost("ssl.smsgo.com.tw")
            .setPath("/sms_gw/sendsms.aspx").setParameter("username", "")
            .setParameter("password", "").setParameter("dstaddr", "")
            .setParameter("encoding", "BIG5")
            .setParameter("smbody", "%E7%B0%A1%E8%A8%8A%E5%85%A7%E5%AE%B9").build();
    HttpGet httpGet = new HttpGet(uri);
    System.out.println(httpGet.getURI());

    System.out.println("Executing request " + httpGet.getRequestLine());

    // Create a custom response handler
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

      public String handleResponse(final HttpResponse response) throws ClientProtocolException,
          IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
          HttpEntity entity = response.getEntity();
          return entity != null ? EntityUtils.toString(entity) : null;
        } else {
          throw new ClientProtocolException("Unexpected response status: " + status);
        }
      }

    };
    try {
      String responseBody = httpclient.execute(httpGet, responseHandler);
      System.out.println("----------------------------------------");
      System.out.println(responseBody);
    } finally {
      httpclient.close();
    }


    return null;

  }


}
