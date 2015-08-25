package pubsub;

public class TestObject {
  private String para1;
  private String para2;
  
  public String getPara1() {
    return para1;
  }

  public void setPara1(String para1) {
    this.para1 = para1;
  }

  public String getPara2() {
    return para2;
  }

  public void setPara2(String para2) {
    this.para2 = para2;
  }

  public TestObject(){}
  
  public TestObject(String para1, String para2) {
    this.para1 = para1;
    this.para2 = para2;
  }
}
