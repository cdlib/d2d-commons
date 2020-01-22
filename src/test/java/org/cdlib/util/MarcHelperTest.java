package org.cdlib.util;

import static org.junit.Assert.*;
import org.cdlib.util.marc2.Marc4jHelper;
import org.cdlib.util.marc2.MarcDataReferenceException;
import org.junit.Before;
import org.junit.Test;

public class MarcHelperTest {

  Marc4jHelper marcHelper;

  @Before
  public void init() throws DeserializationException {
    String marcXML = FileUtil.read("bib/isbn9781847171481.xml");
    marcHelper = new Marc4jHelper(marcXML);
  }

  @Test
  public void leaderchar_happyPath() throws MarcDataReferenceException {
    assertEquals('c', marcHelper.leaderChar(5));
  }

  @Test(expected = MarcDataReferenceException.class)
  public void leadercharBadIndex_throwException() throws MarcDataReferenceException {
    assertEquals('c', marcHelper.leaderChar(90));
  }
  
  @Test
  public void leaderSegment_happyPath() throws MarcDataReferenceException {
    char[] expected = {'c', 'a', 'm'};
    assertArrayEquals(expected, marcHelper.leaderSegment(5, 8));
  }
  
  @Test(expected = MarcDataReferenceException.class)
  public void leaderSegmentBadIndex_throwsException() throws MarcDataReferenceException {
    marcHelper.leaderSegment(8, 5);
  }
  
  @Test(expected = MarcDataReferenceException.class)
  public void leaderSegmentBadIndex2_throwsException() throws MarcDataReferenceException {
    marcHelper.leaderSegment(100, 101);
  }

  @Test
  public void controlFieldChar_happyPath() throws MarcDataReferenceException {
    assertEquals('2', marcHelper.controlFieldChar("008", 7));
  }

  @Test(expected = MarcDataReferenceException.class)
  public void whenNoField_throwsException() throws MarcDataReferenceException {
    marcHelper.controlFieldChar("9999", 3);
  }

  @Test(expected = MarcDataReferenceException.class)
  public void whenNotControlField_throwsException() throws MarcDataReferenceException {
    marcHelper.controlFieldChar("245", 3);
  }

  @Test
  public void controlFieldSegment_happyPath() throws MarcDataReferenceException {
    char[] expected = {'7', '5', '1'};
    assertArrayEquals(expected, marcHelper.controlFieldSegment("001", 0, 3));
  }
  
  @Test(expected = MarcDataReferenceException.class)
  public void controlFieldSegmentBadRange_throwsException() throws MarcDataReferenceException {
    marcHelper.controlFieldSegment("001", 90, 93);
  }
  
  @Test(expected = MarcDataReferenceException.class)
  public void controlFieldSegmentBadField_throwsException() throws MarcDataReferenceException {
    marcHelper.controlFieldSegment("245", 0, 1);
  }

  @Test(expected = MarcDataReferenceException.class)
  public void whenNoControlChar_throwsException() throws MarcDataReferenceException {
    assertNull(marcHelper.controlFieldChar("008", 1000));
  }

  @Test
  public void subfield_happyPath() throws MarcDataReferenceException {
    assertEquals("(William Butler),", marcHelper.subfieldVal("100", "q"));
  }

  @Test(expected = MarcDataReferenceException.class)
  public void noField_throwsException() throws MarcDataReferenceException {
    marcHelper.subfieldVal("999", "a");
  }

  @Test(expected = MarcDataReferenceException.class)
  public void noSubfield_throwsException() throws MarcDataReferenceException {
    marcHelper.subfieldVal("100", "z");
  }


}
