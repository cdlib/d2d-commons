package org.cdlib.domain.objects.bib;

/* 
 * Simplified controlled vocabulary that can be derived 
 * from MARC field 007; class named based on the closely 
 * related MARC field 338 (Carrier Type).
 * 
 * Also possibly coded in the 008.
 * 
 * WEST has algorithm.
 */
public enum CarrierClass {
  
  ONLINE,
  PHYSICAL

}
