package org.cdlib.domain.objects.patron;

import static org.junit.Assert.*;
import org.junit.Test;

public class PatronCategoryTest {

  @Test
  public void unrecognizedDesignation_returnsNull() {
    assertNull(PatronCategory.byDesignation("unknown"));
  }
  
  @Test
  public void testCategoryFromDesignation() {
    assertEquals(PatronCategory.FACULTY, PatronCategory.byDesignation("Faculty"));
  }
  
  @Test
  public void testGetDesignation() {
    assertEquals("staff", PatronCategory.STAFF.getDesignation());
  }

}
