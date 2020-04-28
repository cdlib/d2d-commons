package org.cdlib.domain.objects.link;

/*
 * Defines categories of user access to material targeted by a link.
 */
public enum LinkAccess {

  /*
   * Link access is limited contractually and may be available only to affiliates of specific institutions.
   */
  COMMERCIAL_LICENSE,

  /*
   * Free links are available to anyone but not necessarily public domain and not necessarily governed
   * by an open access agreement This category subsumes OPEN_ACCESS and PUBLIC_DOMAIN, and also
   * includes links that are neither OPEN_ACCESS nor PUBLIC_DOMAIN, but are made freely available at
   * the discretion of the publisher.
   */
  FREE,

  /*
   * Links that are made freely available with an explicit open access license.
   */
  OPEN_ACCESS,

  /*
   * Links that are freely available because they are not copyrighted material.
   */
  PUBLIC_DOMAIN,

  /*
   * Links made available by the HathiTrust Emergency Temporary Access service.
   */
  HATHI_ETAS, 
  
  /*
   * The link access could not be determined.
   */
  UNKNOWN;

}
