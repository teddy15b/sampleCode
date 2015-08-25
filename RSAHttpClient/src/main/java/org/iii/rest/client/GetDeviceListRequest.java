package org.iii.rest.client;

public class GetDeviceListRequest {
  private String token;
  private int page;
  private int records;

  public GetDeviceListRequest(String token, int page, int records) {
    this.token = token;
    this.page = page;
    this.records = records;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getRecords() {
    return records;
  }

  public void setRecords(int records) {
    this.records = records;
  }

}
