package org.cdlib.domain.objects.consortium;

import java.util.Optional;

public enum InstitutionCode  {

  CRL,
  NRLF,
  SRLF,
  UCB,
  UCD,
  UCI,
  UCLA,
  UCM,
  UCR,
  UCSB,
  UCSC,
  UCSD,
  UCSF;

  public static Optional<InstitutionCode> fromString(String code) {
    for (InstitutionCode enumCode : InstitutionCode.values()) {
      if (enumCode.toString().equalsIgnoreCase(code)) {
        return Optional.of(enumCode);
      }
    }
    return Optional.empty();
  }

}
