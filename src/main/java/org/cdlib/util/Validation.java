package org.cdlib.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.validator.routines.EmailValidator;

public class Validation {

  private Validation() {
  }

  /**
   *
   * @param email
   * @return true if the address is considered valid by BOTH apache
   * commons-validate and javax.mail
   */
  public static boolean isLegalEmailAddress(String email) {
    if (email == null) {
      return false;
    }
    email = email.trim();
    EmailValidator validator = EmailValidator.getInstance();
    boolean isValid = validator.isValid(email);
    try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
    } catch (AddressException e) {
      isValid = false;
    }
    return isValid;
  }

}
