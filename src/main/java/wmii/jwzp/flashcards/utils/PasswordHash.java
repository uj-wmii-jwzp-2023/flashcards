package wmii.jwzp.flashcards.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordHash {
  public static String hash(String passwordToHash) {

    try {
      SecureRandom random = new SecureRandom();
      byte[] salt = new byte[16];
      random.nextBytes(salt);
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(salt);
      byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
      return new String(hashedPassword, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Boolean compare(String providedPassword, String hashedString) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte[] hashedPassword = md.digest(providedPassword.getBytes(StandardCharsets.UTF_8));
      return new String(hashedPassword, StandardCharsets.UTF_8).equals(hashedString);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
