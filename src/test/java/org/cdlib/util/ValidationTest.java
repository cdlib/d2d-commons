package org.cdlib.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ValidationTest {
    @Test
  public void testEmailValidationShouldSucceed() {
    assertTrue(Validation.isLegalEmailAddress("joe@berkeley.edu"));
    assertTrue(Validation.isLegalEmailAddress("joe.joe@ucdavis.edu"));
    assertTrue(Validation.isLegalEmailAddress("patron@ucdavis.edu"));
    assertTrue(Validation.isLegalEmailAddress("email@[123.123.123.123]"));
    assertTrue(Validation.isLegalEmailAddress(" patron@ucdavis.edu "));
    assertTrue(Validation.isLegalEmailAddress(" patron@ucdavis.edu "));
    assertTrue(Validation.isLegalEmailAddress("#!$%&'*+-/=?^_`{}|~@example.org"));
    assertTrue(Validation.isLegalEmailAddress("Joe.Smith+filter@domain.com"));
  }

  @Test
  public void testEmailValidationShouldFail() {
    assertFalse(Validation.isLegalEmailAddress("joe@berkeley"));
    assertFalse(Validation.isLegalEmailAddress("joe..@berkeley"));
    assertFalse(Validation.isLegalEmailAddress("joe@@berkeley"));
    assertFalse(Validation.isLegalEmailAddress("email@123.123.123.123"));
    assertFalse(Validation.isLegalEmailAddress(""));
    assertFalse(Validation.isLegalEmailAddress("  "));
    assertFalse(Validation.isLegalEmailAddress("joe@"));
    assertFalse(Validation.isLegalEmailAddress("#@%^%#$@#$@#.com"));
    assertFalse(Validation.isLegalEmailAddress("@ucdavis.edu "));
    assertFalse(Validation.isLegalEmailAddress("ucdavis.edu"));
    assertFalse(Validation.isLegalEmailAddress("Joe Smith <email@domain.com>"));
    assertFalse(Validation.isLegalEmailAddress("email.domain.com"));
    assertFalse(Validation.isLegalEmailAddress("email@domain@domain.com"));
    assertFalse(Validation.isLegalEmailAddress(".email@domain.com"));
    assertFalse(Validation.isLegalEmailAddress("email.@domain.com"));
    assertFalse(Validation.isLegalEmailAddress("あいうえお@domain.com"));
    assertFalse(Validation.isLegalEmailAddress("joe@あいうえお.com"));
    assertFalse(Validation.isLegalEmailAddress("email@domain.com (Joe Smith)"));
    assertFalse(Validation.isLegalEmailAddress("email@domain"));
    assertFalse(Validation.isLegalEmailAddress("email@-domain.com"));
    assertFalse(Validation.isLegalEmailAddress("email@domain.web"));
    assertFalse(Validation.isLegalEmailAddress("email@111.222.333.44444"));
    assertFalse(Validation.isLegalEmailAddress("email@domain..com"));

  }

}
