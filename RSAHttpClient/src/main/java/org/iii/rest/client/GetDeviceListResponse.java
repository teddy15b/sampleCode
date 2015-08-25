package org.iii.rest.client;

import java.util.ArrayList;
import java.util.List;


public class GetDeviceListResponse {
  private List<Device> deviceList;
  private int records;
  private int total;

  public GetDeviceListResponse() {

  }

  public List<Device> getDeviceList() {
    return deviceList;
  }

  public void setDeviceList(List<Device> deviceList) {
    this.deviceList = deviceList;
  }

  public int getRecords() {
    return records;
  }

  public void setRecords(int records) {
    this.records = records;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

 

}
