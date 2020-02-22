package org.cdlib.domain.objects.consortium;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.cdlib.util.JSON;

/**
 * Represents the shelf where a library resource is held.
 * 
 * The shelf may be physical, or virtual, if the items held are electronic.
 * 
 * Data in ShelvingLocation are obtained from the CDL-managed shelving location tables.
 * 
 */
public class ShelvingLocation {

  private OpacInstitution opacInstitution;
  private String name;
  private String opacShelvingLocationId;
  private String oclcHoldingSymbol;

  @NotNull(message = "Shelving location storage institution code is required.")
  private InstitutionCode lendingInstitution;

  private String vdxLocation;
  private String lhrCode;
  private String note;

  @NotNull(message = "Shelving location lending policy is required.")
  private LendingPolicy lendingPolicy;

  @NotNull(message = "Collection type required.")
  private CollectionType collectionType;

  public ShelvingLocation() {}

  public ShelvingLocation(ShelvingLocation source) {
    this.opacInstitution = source.opacInstitution;
    this.name = source.name;
    this.opacShelvingLocationId = source.opacShelvingLocationId;
    this.oclcHoldingSymbol = source.oclcHoldingSymbol;
    this.lendingInstitution = source.lendingInstitution;
    this.vdxLocation = source.vdxLocation;
    this.lhrCode = source.lhrCode;
    this.note = source.note;
    this.lendingPolicy = source.lendingPolicy;
    this.collectionType = source.collectionType;
  }

  public ShelvingLocation(OpacInstitution recordLocation, String name, String opacCode, String oclcHoldingSymbol, InstitutionCode storageLocation, String vdxLocation, String lhrCode, String note,
      LendingPolicy lendingPolicy, CollectionType collectionType) {
    this.opacInstitution = recordLocation;
    this.name = name;
    this.opacShelvingLocationId = opacCode;
    this.oclcHoldingSymbol = oclcHoldingSymbol;
    this.lendingInstitution = storageLocation;
    this.vdxLocation = vdxLocation;
    this.lhrCode = lhrCode;
    this.note = note;
    this.lendingPolicy = lendingPolicy;
    this.collectionType = collectionType;
  }

  /**
   * 
   * The type of collection held at the shelving location.
   * 
   */
  public CollectionType getCollectionType() {
    return collectionType;
  }

  /**
   * The full name of the shelving location as specified in the CDL shelving location table.
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * The OCLC holding symbol for the institution.
   * 
   * The holding symbol used by OCLC to represent the institution.
   * 
   */
  public String getOclcHoldingSymbol() {
    return oclcHoldingSymbol;
  }

  public void setOclcHoldingSymbol(String oclcHoldingSymbol) {
    this.oclcHoldingSymbol = oclcHoldingSymbol;
  }

  /**
   * 
   * The policy specifying whether the lending institution will lend the item.
   * 
   */
  public LendingPolicy getLendingPolicy() {
    return lendingPolicy;
  }

  public void setLendingPolicy(LendingPolicy lendingPolicy) {
    this.lendingPolicy = lendingPolicy;
  }

  /**
   * The LHR code for the shelving location as specified in the CDL shelving location table.
   */
  public String getLhrCode() {
    return lhrCode;
  }

  public void setLhrCode(String lhrCode) {
    this.lhrCode = lhrCode;
  }

  /**
   * The institution that handles loans for this shelving location.
   * 
   * The lending institution is not necessarily the same as the institution that has the OPAC record,
   * as the lending institution may be an RLF.
   */
  public InstitutionCode getLendingInstitution() {
    return lendingInstitution;
  }

  /**
   * A freeform note with information about the shelving location.
   */
  public String getNote() {
    return note;
  }

  /**
   * The local identifier for the shelving location as specified in the OPAC where the record for this
   * shelving location is stored.
   */
  public String getOpacShelvingLocationId() {
    return opacShelvingLocationId;
  }

  /**
   * The lending lending unit that handles this shelving location, as identified to VDX.
   */
  public String getVdxLocation() {
    return vdxLocation;
  }

  public void setCollectionType(CollectionType collectionType) {
    this.collectionType = collectionType;
  }

  public void setLendingInstitution(InstitutionCode institution) {
    lendingInstitution = institution;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public void setOpacShelvingLocationId(String opacShelvingLocationId) {
    this.opacShelvingLocationId = opacShelvingLocationId;
  }

  public void setVdxLocation(String vdxLocation) {
    this.vdxLocation = vdxLocation;
  }

  /**
   * The institution in whose OPAC the record for the shelving location is stored.
   * 
   * This institution will generally be the same as the lending institution, except when the item is
   * stored at an RLF, in which case the RLF handles the lending of items held at this shelving
   * location.
   * 
   */
  public OpacInstitution getOpacInstitution() {
    return opacInstitution;
  }

  public void setOpacInstitution(OpacInstitution campus) {
    this.opacInstitution = campus;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 19 * hash + Objects.hashCode(this.opacInstitution);
    hash = 19 * hash + Objects.hashCode(this.name);
    hash = 19 * hash + Objects.hashCode(this.opacShelvingLocationId);
    hash = 19 * hash + Objects.hashCode(this.oclcHoldingSymbol);
    hash = 19 * hash + Objects.hashCode(this.lendingInstitution);
    hash = 19 * hash + Objects.hashCode(this.vdxLocation);
    hash = 19 * hash + Objects.hashCode(this.lhrCode);
    hash = 19 * hash + Objects.hashCode(this.note);
    hash = 19 * hash + Objects.hashCode(this.lendingPolicy);
    hash = 19 * hash + Objects.hashCode(this.collectionType);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ShelvingLocation other = (ShelvingLocation) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.opacShelvingLocationId, other.opacShelvingLocationId)) {
      return false;
    }
    if (!Objects.equals(this.oclcHoldingSymbol, other.oclcHoldingSymbol)) {
      return false;
    }
    if (!Objects.equals(this.vdxLocation, other.vdxLocation)) {
      return false;
    }
    if (!Objects.equals(this.lhrCode, other.lhrCode)) {
      return false;
    }
    if (!Objects.equals(this.note, other.note)) {
      return false;
    }
    if (this.opacInstitution != other.opacInstitution) {
      return false;
    }
    if (this.lendingInstitution != other.lendingInstitution) {
      return false;
    }
    if (this.lendingPolicy != other.lendingPolicy) {
      return false;
    }
    if (this.collectionType != other.collectionType) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
