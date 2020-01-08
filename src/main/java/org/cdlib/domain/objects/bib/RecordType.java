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
  
  LANGUAGE_MATERIAL,
  NOTATED_MUSIC,
  CARTOGRAPHIC_MATERIAL,
  NON_MUSICAL_SOUND_RECORDING,
  OTHER;

}
