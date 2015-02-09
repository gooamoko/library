package ru.edu.pgtk.library.ejb;

import java.security.MessageDigest;
import javax.ejb.EJBException;

public class Utils {

  private final static String SALT = "salt";

  public static String getHash(final String password) {
    try {
      String pwd = password + SALT;
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(pwd.getBytes());
      byte hash[] = md.digest();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < hash.length; i++) {
        sb.append(String.format("%02x", hash[i]));
      }
      return sb.toString();
    } catch (Exception e) {
      throw new EJBException("Can't compute password hash!");
    }
  }

}
