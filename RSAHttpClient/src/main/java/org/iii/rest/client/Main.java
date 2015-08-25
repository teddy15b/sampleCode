package org.iii.rest.client;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
  public static String id = "intrauser";
  public static String pw = "EvUgljM^t1M&*#TV";
  public static String loginUrl = "http://iiimeterweb.azurewebsites.net/api/Intra/login";
  public static String getDeviceListUrl =
      "http://iiimeterweb.azurewebsites.net/api/Intra/getDeviceList";

  public static void main(String[] args) throws JsonParseException, JsonMappingException,
      IOException {

    // get access token
    LoginRequest loginRequest = new LoginRequest(id, pw);
    String jsonToken = new Client().post(loginUrl, loginRequest);
    LoginResponse loginResponse = new ObjectMapper().readValue(jsonToken, LoginResponse.class);

    // get device list
    GetDeviceListRequest deviceListRequest =
        new GetDeviceListRequest(loginResponse.getToken(), 1, 40);
    String jsonDeviceList = new Client().post(getDeviceListUrl, deviceListRequest);

    GetDeviceListResponse getDeviceListResponse =
        new ObjectMapper().readValue(jsonDeviceList, GetDeviceListResponse.class);

    List<Device> deviceList = getDeviceListResponse.getDeviceList();

    System.out.println("{");
    System.out.println("  \"DeviceList\":[");
    for (Device device : deviceList) {
      System.out.println("  {");
      System.out.println("    \"DEVICE_ID\":" + "\"" + device.getDeviceId() + "\",");
      System.out.println("    \"TYPE\":" + "\"" + device.getType() + "\",");
      System.out.println("    \"CIRCUIT\":" + "\"" + device.getCircuit() + "\",");
      System.out.println("    \"Feed_ID\":" + "\"" + device.getFeedId() + "\",");
      System.out.println("    \"API_KEY\":" + "\"" + device.getApiKey() + "\"");
      System.out.println("  },");
    }
    System.out.println("],");
    System.out.println("  \"records\":" + "\"" + getDeviceListResponse.getRecords() + "\",");
    System.out.println("  \"total\":" + "\"" + getDeviceListResponse.getTotal() + "\"");
    System.out.println("}");
  }

}
