package test;

import java.util.*;

public class HashMapDemo {
  public static void main(String a[]){
    List<String> syncal = 
      Collections.synchronizedList(new ArrayList<String>());

    //Adding elements to synchronized ArrayList
    syncal.add("Pen");
    syncal.add("NoteBook");
    syncal.add("Ink");

    System.out.println("Iterating synchronized ArrayList:");
    synchronized(syncal) {
    Iterator<String> iterator = syncal.iterator(); 
    while (iterator.hasNext())       
       System.out.println(iterator.next());
    }
}
}