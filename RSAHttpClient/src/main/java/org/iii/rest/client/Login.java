package org.iii.rest.client;

public class Login {
  private String id;
  private String pw;

  public Login(String id, String pw) {
    this.id = id;
    this.pw = pw;
    this.ts = System.currentTimeMillis() / 1000L;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPw() {
    return pw;
  }

  public void setPw(String pw) {
    this.pw = pw;
  }

  public long getTs() {
    return ts;
  }

  public void setTs(long ts) {
    this.ts = ts;
  }

  private long ts;



}
