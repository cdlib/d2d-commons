package org.cdlib.util.marc2;

import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.impl.SubfieldImpl;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

public class SubFieldsTest {
  
  private DataField mockDataField = mock(DataField.class);
  private List<Subfield> marc4jSubfields = new ArrayList<>();
  
  @Before
  public void init() {
    
    Subfield sub0 = new SubfieldImpl('a');
    sub0.setData("First of repeating");
    marc4jSubfields.add(sub0);
    
    Subfield sub1 = new SubfieldImpl('a');
    sub1.setData("Second of repeating");
    marc4jSubfields.add(sub1);
    
    Subfield sub2 = new SubfieldImpl('b');
    sub2.setData("Non repeating");
    marc4jSubfields.add(sub2);
    
    when(mockDataField.getSubfields()).thenReturn(marc4jSubfields);
  }
  
  @Test
  public void testGet() {
    SubFields subfields = new SubFields(mockDataField);
    assertEquals("First of repeating", subfields.get('a').get().get(0));
    assertEquals("Second of repeating", subfields.get('a').get().get(1));
    assertEquals("Non repeating", subfields.get('b').get().get(0));
    List<String> result = subfields.get('a', 'b').get();
    assertEquals(3, result.size());
    assertEquals("First of repeating", result.get(0));
    assertEquals("Second of repeating", result.get(1));
    assertEquals("Non repeating", result.get(2));
  }

}
