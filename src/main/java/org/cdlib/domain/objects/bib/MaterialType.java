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
public enum MaterialType {
  
  LANGUAGE_RESOURCE,
  MUSICAL_SCORE,
  CARTOGRAPHIC_RESOURCE,
  AUDIO_RESOURCE,
  VIDEO_RESOURCE,
  COMPUTER_RESOURCE,
  GRAPHIC_RESOURCE,
  PHYSICAL_ARTIFACT,
  MIXED_MEDIA

}
