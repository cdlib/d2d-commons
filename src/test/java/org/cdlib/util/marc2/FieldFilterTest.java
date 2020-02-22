package org.cdlib.util.marc2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.cdlib.util.marc2.FieldFilter.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.cdlib.util.DeserializationException;
import org.cdlib.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

public class FieldFilterTest {
  
  FieldFilter marcHelper;

  @Before
  public void init() throws DeserializationException {
    String marcXML = FileUtil.read("bib/isbn9781847171481.xml");
    marcHelper = new FieldFilter(marcXML);
  }
  
  @Test
  public void marcDataFields_happyPath() {
    Optional<List<MarcDataField>> result = marcHelper.marcDataFields("020");
    List<MarcDataField> resultList = result.get();
    assertEquals(2, resultList.size());
    assertEquals("9781847171481", resultList.get(0)
                                            .getSubFields()
                                            .get('a')
                                            .get()
                                            .get(0));
  }

  @Test
  public void marcDataFieldsWithFilter_happyPath() {
    Predicate<MarcDataField> test = (df) -> df.getSubFields()
                                              .get('a')
                                              .get()
                                              .get(0)
                                              .equals("9781847171481");
    Optional<List<MarcDataField>> result = marcHelper.marcDataFields("020", test);
    List<MarcDataField> resultList = result.get();
    assertEquals(1, resultList.size());
    assertEquals("9781847171481", resultList.get(0)
                                            .getSubFields()
                                            .get('a')
                                            .get()
                                            .get(0));
  }

  @Test
  public void marcDataFieldsWithFilterSubfieldNotFound_emptyResult() {
    Optional<List<MarcDataField>> result = marcHelper.marcDataFields("020", hasSubfieldMatching('x', "9781847171481"));
    List<MarcDataField> resultList = result.get();
    assertEquals(0, resultList.size());
  }
  
  @Test
  public void marcDataFieldsWithControlFieldTag_emptyResult() {
    Optional<List<MarcDataField>> result = marcHelper.marcDataFields("008", hasSubfieldMatching('x', "9781847171481"));
    List<MarcDataField> resultList = result.get();
    assertEquals(0, resultList.size());
  }
  
  @Test
  public void marcDataFieldsWithFilterTagNotFound_emptyResult() {
    Optional<List<MarcDataField>> result = marcHelper.marcDataFields("999", hasSubfieldMatching('a', "9781847171481"));
    List<MarcDataField> resultList = result.get();
    assertEquals(0, resultList.size());
  }
  
  @Test
  public void marcDataFieldsWithFilterTagNull_emptyResult() {
    Optional<List<MarcDataField>> result = marcHelper.marcDataFields(null, hasSubfieldMatching('a', "9781847171481"));
    List<MarcDataField> resultList = result.get();
    assertEquals(0, resultList.size());
  }

  @Test
  public void marcDataFieldsNotFound_emptyList() {
    Optional<List<MarcDataField>> result = marcHelper.marcDataFields("776");
    List<MarcDataField> resultList = result.get();
    assertTrue(resultList.isEmpty());
  }

}
