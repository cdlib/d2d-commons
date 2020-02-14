package org.cdlib.util.marc2;

import org.marc4j.marc.DataField;

/*
 * 
 * A wrapper around a MARC4J DataField
 * that includes our SubFields object.
 * 
 */
public class MarcDataField {
  
  private String tag;
  private SubFields subFields;
  private char[] indicators;
  
  public MarcDataField(DataField dataField) {
    tag = dataField.getTag();
    subFields = new SubFields(dataField);
    indicators = MarcRecordHelper.indicators(dataField);
  }

  public MarcDataField(String tag, SubFields subFields, char[] indicators) {
    this.tag = tag;
    this.subFields = subFields;
    this.indicators = indicators;
  } 

  public String getTag() {
    return tag;
  }

  public SubFields getSubFields() {
    return subFields;
  }

  public char[] getIndicators() {
    return indicators;
  }

}
