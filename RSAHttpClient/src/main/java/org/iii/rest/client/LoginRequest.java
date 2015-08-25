package org.iii.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginRequest {
  private String data;

  public LoginRequest(String id, String pw) {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = null;
    
    try {
      json = objectMapper.writeValueAsString(new Login(id, pw));
      this.data = new RSAEncoder().encrypt(json);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

}
