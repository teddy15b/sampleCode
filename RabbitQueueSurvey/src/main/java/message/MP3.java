/**
 * 
 */
package message;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * @author teddy
 *
 */
public class MP3 {

  /**
   * @param args
   */
  public static void main(String[] args) {
    playSound();
  }

  public static void playSound() {
    try {
      AudioInputStream audioInputStream =
          AudioSystem.getAudioInputStream(new File("alarm.wav").getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      // FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      // volume.setValue(3.0f); // Reduce volume by 10 decibels.
      final CountDownLatch clipDone = new CountDownLatch(1);
      clip.addLineListener(new LineListener() {
          public void update(LineEvent event) {
            System.out.println(event.getType().toString());
              if (event.getType() == LineEvent.Type.STOP) {
                  event.getLine().close();
                  clipDone.countDown();
              }
          }
      });
      clip.setFramePosition(0);
      clip.start();
      clipDone.await();
    } catch (Exception ex) {
      System.out.println("Error with playing sound.");
      ex.printStackTrace();
    }
  }

}
