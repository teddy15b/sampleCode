package cwms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author teddy
 *
 *         Oct 13, 2016
 */
public class Rearrangement {

  public static void main(String[] args) {
    try (Stream<Path> paths = Files.walk(Paths.get("in"))) {
      paths.forEach(filePath -> {
        if (Files.isRegularFile(filePath)) {
          //System.out.println(filePath);
          handle(filePath.toString());
        }
      });
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static void handle(String readFilePath) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(readFilePath));
      ArrayList<String> cameraList = new ArrayList<String>();
      ArrayList<String> dataList = new ArrayList<String>();

      for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        if (line.startsWith("330D01")) {
          cameraList.add(line);
        } else {
          dataList.add(line);
        }
      }
      
      String array[] = readFilePath.split("/");      
      String writeFilePath = String.format("%s%s", "out/", array[1]);
      File file = new File(writeFilePath);
      file.getParentFile().mkdirs();
      
      PrintWriter writer = new PrintWriter(file);
      System.out.println("file created: "+file.getPath());
      
      for (String s : dataList)
        //System.out.println(s);
        writer.println(s);
      
      for (String s : cameraList)
        //System.out.println(s);
        writer.println(s);
      
      writer.flush();
      writer.close();
      
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
