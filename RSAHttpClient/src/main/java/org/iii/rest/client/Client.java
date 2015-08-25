package org.iii.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {
  private static final String USER_AGENT = "III";

  public String post(String url, Object data) {
    HttpClient client = new DefaultHttpClient();
    HttpPost post = new HttpPost(url);
    String receiveData = null;

    // add header
    post.setHeader("User-Agent", USER_AGENT);
    post.setHeader("Content-type", "application/json");

    try {
      post.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(data)));
      HttpResponse response;
      response = client.execute(post);
      BufferedReader rd =
          new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

      receiveData = rd.readLine();

      // jsonObj = (JSONObject) JSONValue.parse(json);

    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return receiveData;
  }


}
