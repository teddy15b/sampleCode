package org.iii.rest.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {
  @JsonProperty("DEVICE_ID")
  private String deviceId;

  @JsonProperty("TYPE")
  private String type;
  
  @JsonProperty("CIRCUIT")
  private String circuit;
  
  @JsonProperty("FEED_ID")
  private String feedId;
  
  @JsonProperty("API_KEY")
  private String apiKey;

  public Device() {

  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCircuit() {
    return circuit;
  }

  public void setCircuit(String circuit) {
    this.circuit = circuit;
  }

  public String getFeedId() {
    return feedId;
  }

  public void setFeedId(String feedId) {
    this.feedId = feedId;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

}
