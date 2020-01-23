package org.cdlib.domain.objects.bib;

/**
 *
 * Modeled on MARC leader byte 6 - Type of Record.
 * 
 * A classification of the general type of material represented by the Bib data.
 * 
 * See https://www.loc.gov/marc/bibliographic/bdleader.html
 *
 */
public enum RecordType {
  
  BOOK,
  MUSIC,
  MAP,
  COMPUTER_FILE,
  SERIAL,
  VISUAL_MATERIALS,
  MIXED_MATERIALS,
  OTHER;

}
