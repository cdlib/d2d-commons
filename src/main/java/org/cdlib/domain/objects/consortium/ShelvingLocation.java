package org.cdlib.domain.objects.consortium;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class ShelvingLocation {

    private OpacLocation recordLocation;
    
    private String name;
    private String opacShelvingLocationId;
    private String oclcHoldingSymbol;
    
    @NotNull(message = "Shelving location storage institution code is required.")
    private InstitutionCode storageLocation;
    
    private String vdxLocation;
    private String lhrCode;
    private String note;
    
    @NotNull(message = "Shelving location lending policy is required.")
    private LendingPolicy lendingPolicy;
    
    @NotNull(message = "Collection type required.")
    private CollectionType collectionType;

    public ShelvingLocation() {
    }

    public ShelvingLocation(ShelvingLocation source) {
        this.recordLocation = source.recordLocation;
        this.name = source.name;
        this.opacShelvingLocationId = source.opacShelvingLocationId;
        this.oclcHoldingSymbol = source.oclcHoldingSymbol;
        this.storageLocation = source.storageLocation;
        this.vdxLocation = source.vdxLocation;
        this.lhrCode = source.lhrCode;
        this.note = source.note;
        this.lendingPolicy = source.lendingPolicy;
        this.collectionType = source.collectionType;
    }

    public ShelvingLocation(OpacLocation recordLocation, String name, String opacCode, String oclcHoldingSymbol, InstitutionCode storageLocation, String vdxLocation, String lhrCode, String note, LendingPolicy lendingPolicy, CollectionType collectionType) {
        this.recordLocation = recordLocation;
        this.name = name;
        this.opacShelvingLocationId = opacCode;
        this.oclcHoldingSymbol = oclcHoldingSymbol;
        this.storageLocation = storageLocation;
        this.vdxLocation = vdxLocation;
        this.lhrCode = lhrCode;
        this.note = note;
        this.lendingPolicy = lendingPolicy;
        this.collectionType = collectionType;
    }
    
    public CollectionType getCollectionType() {
      return collectionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOclcHoldingSymbol() {
        return oclcHoldingSymbol;
    }

    public void setOclcHoldingSymbol(String oclcHoldingSymbol) {
        this.oclcHoldingSymbol = oclcHoldingSymbol;
    }

    public LendingPolicy getLendingPolicy() {
        return lendingPolicy;
    }

    public void setLendingPolicy(LendingPolicy lendingPolicy) {
        this.lendingPolicy = lendingPolicy;
    }

    public String getLhrCode() {
        return lhrCode;
    }

    public void setLhrCode(String lhrCode) {
        this.lhrCode = lhrCode;
    }

    public InstitutionCode getStorageLocation() {
        return storageLocation;
    }

    public String getNote() {
        return note;
    }

    public String getOpacShelvingLocationId() {
        return opacShelvingLocationId;
    }

    public String getVdxLocation() {
        return vdxLocation;
    }
    
    public void setCollectionType (CollectionType collectionType) {
      this.collectionType = collectionType;
    }

    public void setStorageLocation(InstitutionCode institution) {
        storageLocation = institution;
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

    public OpacLocation getRecordLocation() {
        return recordLocation;
    }

    public void setRecordLocation(OpacLocation campus) {
        this.recordLocation = campus;
    }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 19 * hash + Objects.hashCode(this.recordLocation);
    hash = 19 * hash + Objects.hashCode(this.name);
    hash = 19 * hash + Objects.hashCode(this.opacShelvingLocationId);
    hash = 19 * hash + Objects.hashCode(this.oclcHoldingSymbol);
    hash = 19 * hash + Objects.hashCode(this.storageLocation);
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
    if (this.recordLocation != other.recordLocation) {
      return false;
    }
    if (this.storageLocation != other.storageLocation) {
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
    return "ShelvingLocation{" + "recordLocation=" + recordLocation + ", name=" + name + ", opacShelvingLocationId=" + opacShelvingLocationId + ", oclcHoldingSymbol=" + oclcHoldingSymbol + ", storageLocation=" + storageLocation + ", vdxLocation=" + vdxLocation + ", lhrCode=" + lhrCode + ", note=" + note + ", lendingPolicy=" + lendingPolicy + ", collectionType=" + collectionType + '}';
  }
    
}
