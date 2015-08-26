package spring.security.boot.mongodb.security;

import java.util.Base64;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * @author Teddy
 */

public class AesService {

  public static final Logger logger = LoggerFactory.getLogger(AesService.class);

  public static Optional<String> Encrypt(String sSrc, String sKey) throws Exception {
    if (sKey == null) {
      logger.error("key is null");
      return Optional.empty();
    }
    // check key size
    if (sKey.length() != 16) {
      logger.error("key size is not 16");
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

  public static Optional<String> Decrypt(String sSrc, String sKey) throws Exception {
    if (sKey == null) {
      logger.error("key is null");
      return Optional.empty();
    }

    if (sKey.length() != 16) {
      logger.error("key size is not 16");
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

//  public static void main(String[] args) throws Exception {
//    AesService aes = new AesService();
//    String cKey = "1234567890abcdef";
//    String cSrc = "testtest11111111";
//    logger.info(cSrc);
//    // encrypt
//    Optional<String> enString = aes.Encrypt(cSrc, cKey);
//    if (enString.isPresent()) {
//      logger.info("encrypt string：" + enString.get());
//      // decrypt
//      Optional<String> deString = aes.Decrypt(enString.get(), cKey);
//      if (deString.isPresent()) {
//        logger.info("decrypt string：" + deString.get());
//      } else {
//        logger.error("decrypt failed");
//      }
//
//    } else {
//      logger.error("encrypt failed");
//    }
//  }
}
