package org.cdlib.domain.objects.bib;

/**
 *
 * Modeled on MARC leader byte 19.
 * 
 * Indicates whether the cataloged item consists of a set of discrete items,
 * or whether it is a member of such a set.
 * 
 * This setting will be null for bib items that consists of a single discrete item that is not a member of a multipart cataloged Bib.
 * 
 * See https://www.loc.gov/marc/bibliographic/bdleader.html
 * 
 * @author jferrie
 */
public enum MultipartResourceRecordLevel {
  
  SET,
  PART_WITH_INDEPENDENT_TITLE,
  PART_WITH_DEPENDENT_TITLE;

}
