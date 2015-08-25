package org.iii.rest.client;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;


public class RSAEncoder {

  private static String modulus =
      "y//qVNmOEJv9oERmsLIm2DPr4bRO4WJs4v/SyhXi0VbyO4EIJc5n0ozT0cL2r6OBK9BJmZEL9fVOK7wbbmFrN79osYSSLyG4HOWlzLW91JUYdvHyj5fdUCl0cAEx3OZdStzunmQXCP3ss8/dNiekit/90oqxcGbfF6Hj506CQB8=";
  private static String exponent = "AQAB";


  public String encrypt(String s) throws Exception {
    Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);
    byte[] utf8 = s.getBytes("UTF-8");
    byte[] data = cipher.doFinal(utf8);

    return DatatypeConverter.printBase64Binary(data);
  }

  private Cipher createCipher(int opmode) throws Exception {
    PublicKey publicKey = getPublicKey(modulus, exponent);
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
    cipher.init(opmode, publicKey);
    return cipher;
  }

  private PublicKey getPublicKey(String modulusBase64, String exponentBase64) throws Exception {
    return getPublicKey(DatatypeConverter.parseBase64Binary(modulusBase64),
        DatatypeConverter.parseBase64Binary(exponentBase64));
  }

  private PublicKey getPublicKey(byte[] modulus, byte[] exponent) throws Exception {
    BigInteger m = new BigInteger(1, modulus);
    BigInteger e = new BigInteger(1, exponent);

    RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(keySpec);

    return publicKey;
  }

}
