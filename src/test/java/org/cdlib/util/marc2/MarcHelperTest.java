package org.cdlib.util.marc2;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.cdlib.util.DeserializationException;
import org.cdlib.util.FileUtil;
import org.cdlib.util.marc2.MarcRecordHelper;
import org.junit.Before;
import org.junit.Test;

public class MarcHelperTest {

  MarcRecordHelper marcHelper;

  @Before
  public void init() throws DeserializationException {
    String marcXML = FileUtil.read("bib/isbn9781847171481.xml");
    marcHelper = new MarcRecordHelper(marcXML);
  }

  @Test
  public void leaderchar_happyPath() {
    assertEquals(new Character('c'), marcHelper.leaderChar(5).get());
  }

  @Test(expected = IllegalArgumentException.class)
  public void leadercharNegativeIndex_throwException() {
    marcHelper.leaderChar(-1).get();
  }

  @Test
  public void leadercharIndexOutOfRange_emptyOptional() {
    assertFalse(marcHelper.leaderChar(100).isPresent());
  }

  @Test
  public void leadercharBadIndex_emptyOptional() {
    assertFalse(marcHelper.leaderChar(90).isPresent());
  }

  @Test
  public void leaderVal_happyPath() {
    assertEquals("00000cam a2200000Ma 4500", marcHelper.leaderVal().get());
  }

  @Test
  public void leaderSegment_happyPath() {
    char[] expected = {'c', 'a', 'm'};
    char[] actual = marcHelper.leaderSegment(5, 8).get();
    assertArrayEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void leaderSegmentBadArgs_throwsException() {
    marcHelper.leaderSegment(8, 5);
  }

  @Test
  public void leaderSegmentOutOfRange_emptyOptional() {
    assertFalse(marcHelper.leaderSegment(100, 101).isPresent());
  }

  @Test
  public void leaderSegmentOutOfRange2_emptyOptional() {
    assertFalse(marcHelper.leaderSegment(0, 101).isPresent());
  }

  @Test
  public void controlFieldVal_happyPath() {
    String expected = "751666086";
    assertEquals(expected, marcHelper.controlFieldVal("001").get());
  }

  @Test(expected = IllegalArgumentException.class)
  public void controlFieldValEmptyTag_illegal() {
    marcHelper.controlFieldVal("");
  }

  @Test(expected = NullPointerException.class)
  public void controlFieldValNullTag_illegal() {
    marcHelper.controlFieldVal(null);
  }

  @Test
  public void controlFieldMissing_optionalEmpty() {
    assertFalse(marcHelper.controlFieldVal("9999").isPresent());
  }

  @Test
  public void notControlField_optionalEmpty() {
    assertFalse(marcHelper.controlFieldVal("245").isPresent());
  }

  @Test
  public void controlFieldVal_optionalHasEmptyString() {
    assertTrue(marcHelper.controlFieldVal("006").isPresent());
    assertTrue(marcHelper.controlFieldVal("006").get().isEmpty());
  }

  @Test(expected = NullPointerException.class)
  public void nullControlField_throwsException() {
    assertFalse(marcHelper.controlFieldVal(null).isPresent());
  }

  @Test
  public void controlFieldChar_happyPath() {
    assertEquals(new Character('2'), marcHelper.controlFieldChar("008", 7).get());
  }

  @Test
  public void controlFieldCharNoField_throwsException() {
    assertFalse(marcHelper.controlFieldChar("9999", 3).isPresent());
  }

  @Test
  public void controlFieldCharNotControlField_throwsException() {
    assertFalse(marcHelper.controlFieldChar("245", 3).isPresent());
  }

  @Test
  public void controlFieldSegment_happyPath() {
    char[] expected = {'7', '5', '1'};
    char[] actual = marcHelper.controlFieldSegment("001", 0, 3).get();
    assertArrayEquals(expected, actual);
  }

  @Test
  public void controlFieldSegmentOutOfRange_emptyOptional() {
    assertFalse(marcHelper.controlFieldSegment("001", 90, 93).isPresent());
  }

  @Test
  public void controlFieldSegmentOutOfRange2_emptyOptional() {
    assertFalse(marcHelper.controlFieldSegment("001", 0, 93).isPresent());
  }

  @Test
  public void controlFieldSegmentNotControlField_emptyOptional() {
    assertFalse(marcHelper.controlFieldSegment("245", 0, 1).isPresent());
  }

  @Test
  public void subfield_happyPath() {
    assertEquals("(William Butler),", marcHelper.subfieldVal("100", 'q').get());
  }

  @Test
  public void subfieldRepeating_getFirstValue() {
    assertEquals("OCLCO", marcHelper.subfieldVal("040", 'd').get());
  }

  @Test
  public void noSuchField_emptyOptional() {
    assertFalse(marcHelper.subfieldVal("999", 'a').isPresent());
  }

  @Test
  public void noSuchSubfield_emptyOptional() {
    assertFalse(marcHelper.subfieldVal("100", 'z').isPresent());
  }

  @Test(expected = IllegalArgumentException.class)
  public void fieldTagEmpty_illegal() {
    marcHelper.subfieldVal("", 'z');
  }

  @Test(expected = NullPointerException.class)
  public void fieldTagNull_illegal() {
    marcHelper.subfieldVal(null, 'z');
  }

  @Test
  public void wholeSubfieldHappyPath() {
    Optional<SubFields> subfields = marcHelper.subFields("020");
    assertTrue(subfields.isPresent());
    assertEquals("9781847171481", subfields.get().get('a').get().get(0));
  }

  @Test
  public void wholeSubfieldTagNotFound_EmptyOptional() {
    Optional<SubFields> subfields = marcHelper.subFields("999");
    assertFalse(subfields.isPresent());
  }

  @Test
  public void wholeSubfieldTagEmpty_EmptyOptional() {
    Optional<SubFields> subfields = marcHelper.subFields("");
    assertFalse(subfields.isPresent());
  }
  
  @Test
  public void subfields_happyPath() {
    Optional<List<String>> result = marcHelper.subfieldVals("020", 'a');
    assertTrue(result.isPresent());
    List<String> innerResult = result.get();
    assertEquals("9781847171481", innerResult.get(0));
    assertEquals("1847171486", innerResult.get(1));
  }
  
  @Test(expected = NullPointerException.class)
  public void subfieldsNullTag_NPE() {
    marcHelper.subfieldVals(null, 'a');
  }
  
  @Test
  public void subfieldsMultiple_happyPath() {
    Optional<List<String>> result = marcHelper.subfieldVals("020", 'a', 'q');
    assertTrue(result.isPresent());
    List<String> innerResult = result.get();
    assertEquals(4, innerResult.size());
    assertEquals("9781847171481", innerResult.get(0));
    assertEquals("1847171486", innerResult.get(1));
    assertEquals("(hbk.)", innerResult.get(2));
    assertEquals("(hbk.)", innerResult.get(3));
  }

  @Test
  public void subfields_happyPath1() {
    Optional<List<String>> result = marcHelper.subfieldVals("100", 'q');
    assertTrue(result.isPresent());
  }

  @Test
  public void multipleSubfield_getAll() {
    Optional<List<String>> result = marcHelper.subfieldVals("040", 'd');
    assertTrue(result.isPresent());
    assertEquals(3, result.get().size());
  }

  @Test
  public void subfieldsNoField_emptyOptional() {
    Optional<List<String>> result = marcHelper.subfieldVals("900", 'a', 'b');
    assertFalse(result.isPresent());
  }

  @Test
  public void subfieldsNotDataField_emptyOptional() {
    Optional<List<String>> result = marcHelper.subfieldVals("001", 'a');
    assertFalse(result.isPresent());
  }

  @Test
  public void subfieldsMissing_emptyOptional() {
    Optional<List<String>> result = marcHelper.subfieldVals("100", 'z');
    assertFalse(result.isPresent());
  }

  @Test
  public void subfieldEmpty_valueEmpty() {
    Optional<String> result = marcHelper.subfieldVal("015", 'a');
    assertTrue(result.get().isEmpty());
  }

  @Test
  public void indicators_HappyPath() {
    List<char[]> indicators = marcHelper.indicators("245").orElse(new ArrayList<>());
    char[] result = indicators.get(0);
    assertEquals('1', result[0]);
    assertEquals('0', result[1]);
  }

  @Test
  public void indicatorsBothEmpty_whitespace() {
    List<char[]> indicators = marcHelper.indicators("015").orElse(new ArrayList<>());
    char[] result = indicators.get(0);
    assertTrue(Character.isWhitespace(result[0]));
    assertTrue(Character.isWhitespace(result[1]));
  }


}
