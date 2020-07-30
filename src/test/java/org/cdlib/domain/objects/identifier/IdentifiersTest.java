package org.cdlib.domain.objects.identifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class IdentifiersTest {
  
  private List<String> altValues;
  
  @Before
  public void init() {
    altValues = alternateValues();
  }
  
  private List<String> alternateValues() {
    String[] vals = {"one", "two", "three"};
    return Arrays.asList(vals);
  }
  
  @Test
  public void testIsbnEquality() {
    ISBN isbn1 = new ISBN("12345");
    ISBN isbn2 = new ISBN("12345");
    assertEquals(isbn2, isbn1);
    isbn2 = new ISBN(isbn1);
    assertEquals(isbn2, isbn1);
    isbn1.setAlternateValues(altValues);
    assertFalse(isbn1.getAlternateValues() == altValues);
  }
  
  @Test
  public void testIssnEquality() {
    ISSN issn1 = new ISSN("12345");
    ISSN issn2 = new ISSN("12345");
    assertEquals(issn2, issn1);
    issn2 = new ISSN(issn1);
    assertEquals(issn2, issn1);
    issn1.setAlternateValues(altValues);
    assertFalse(issn1.getAlternateValues() == altValues);
  }
  
  @Test
  public void testOclcEquality() {
    OclcNumber ocn1 = new OclcNumber("12345");
    OclcNumber ocn2 = new OclcNumber("12345");
    assertEquals(ocn2, ocn1);
    ocn2 = new OclcNumber(ocn1);
    assertEquals(ocn2, ocn1);
    ocn1.setAlternateValues(altValues);
    assertFalse(ocn1.getAlternateValues() == altValues);
  }
  
  @Test
  public void testDoiEquality() {
    DOI doi1 = new DOI("12345");
    DOI doi2 = new DOI("12345");
    assertEquals(doi2, doi1);
    doi2 = new DOI(doi1);
    assertEquals(doi2, doi1);
    doi1.setAlternateValues(altValues);
    assertFalse(doi1.getAlternateValues() == altValues);
  }
  
  @Test
  public void testPmidEquality() {
    PMID pmid1 = new PMID("12345");
    PMID pmid2 = new PMID("12345");
    assertEquals(pmid2, pmid1);
    pmid2 = new PMID(pmid1);
    assertEquals(pmid2, pmid1);
    pmid1.setAlternateValues(altValues);
    assertFalse(pmid1.getAlternateValues() == altValues);
  }
  

}
