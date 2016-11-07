package misc;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.joda.time.DateTime;


public class AesTest {

  private static final String sKEY = "1234567890abcdef";

  public static void main(String[] args) throws Exception {
    System.out.println(Encrypt("dapisno1",sKEY).get());
    System.out.println(AESDecrypt("KwyXlesw9p0pNI4kB+st/g==",sKEY).get());
    
  }
  
  public static Optional<String> Encrypt(String sSrc, String sKey) throws Exception {
    if (sKey == null) {
      System.out.println("key is null");
      return Optional.empty();
    }
    // check key size
    if (sKey.length() != 16) {
      System.out.println("key size is not 16");
      return Optional.empty();
    }
    byte[] raw = sKey.getBytes();
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 算法/模式/補碼方式
    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    byte[] encrypted = cipher.doFinal(sSrc.getBytes());

    return Optional.of(Base64.getEncoder().encodeToString(encrypted));
  }
  
  public static Optional<String> AESDecrypt(String sSrc, String sKey) throws Exception {
    if (sKey == null) {
      System.out.println("key is null");
      return Optional.empty();
    }

    if (sKey.length() != 16) {
      System.out.println("key size is not 16");
      return Optional.empty();
    }
    byte[] raw = sKey.getBytes("ASCII");
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
    byte[] encryptedAES = Base64.getDecoder().decode(sSrc);
    byte[] original = cipher.doFinal(encryptedAES);
    String originalString = new String(original);
    return Optional.of(originalString);
  }

}
