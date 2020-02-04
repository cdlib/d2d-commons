package org.cdlib.domain.objects.bib;


/**
 *
 * Modeled on MARC leader byte 7 - Bibliographic Level.
 * 
 * Indicates the publishing scheme for the bibliographic item:
 * where it is published as a series over time (a serial), or whether it is published 
 * as a single item or set of items within a defined period (a mono).
 * 
 * The term "monograph" is specifically avoided because many library resources are not written things.
 * 
 * See https://www.loc.gov/marc/bibliographic/bdleader.html
 * 
 * 
 */
public enum Seriality {
  
  MONOGRAPH,
  SERIAL,
  INTEGRATING_RESOURCE

}
