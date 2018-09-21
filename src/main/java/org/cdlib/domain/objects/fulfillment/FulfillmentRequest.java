package org.cdlib.domain.objects.fulfillment;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.cdlib.domain.objects.consortium.OfficeLocation;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.holdings.Holdings;
import org.cdlib.domain.objects.patron.Patron;

/**
 * A request made by a patron for access to a library resource. This object
 * contains all of the data needed for an application to process the request.
 */
public class FulfillmentRequest {

  @NotNull(message = "Dispatch method is required.")
  private DispatchMethod dispatchMethod;
  @Valid
  @NotNull(message = "Holdings are required.")
  private Holdings holdings;
  @Valid
  @NotNull(message = "Pickup location is required.")
  private OfficeLocation pickupLocation;
  @Valid
  @NotNull(message = "Processing location is required.")
  private OfficeLocation processingLocation;
  @Valid
  @NotNull(message = "Patron is required.")
  private Patron patron;
  @Valid
  @NotNull(message = "Patron comments are required.")
  private PatronComments patronComments;
  @NotNull(message = "Request source is required.")
  private String requestSource;

  /**
   * Metadata can carry ephemeral information sent by a client (e.g. session &
   * IP address). Applications should not rely on metadata as a data payload.
   */
  private Map<String, Object> metadata;

  public FulfillmentRequest() {
  }

  public FulfillmentRequest(FulfillmentRequest source) {
    this.dispatchMethod = source.dispatchMethod;
    if (source.holdings != null) {
      this.holdings = new Holdings(source.holdings);
    }
    if (source.patron != null) {
      this.patron = new Patron(source.patron);
    }
    if (source.patronComments != null) {
      this.patronComments = new PatronComments(source.patronComments);
    }
    if (source.pickupLocation != null) {
      this.pickupLocation = new OfficeLocation(source.pickupLocation);
    }
    if (source.processingLocation != null) {
      this.processingLocation = new OfficeLocation(source.processingLocation);
    }
    if (source.requestSource != null) {
      this.requestSource = source.requestSource;
    }
    if (source.metadata != null) {
      this.metadata = new TreeMap<>(source.metadata);
    }
  }

  public DispatchMethod getDispatchMethod() {
    return dispatchMethod;
  }

  public void setDispatchMethod(DispatchMethod dispatchMethod) {
    this.dispatchMethod = dispatchMethod;
  }

  public Holdings getHoldings() {
    return holdings;
  }

  public void setHoldings(Holdings holdings) {
    this.holdings = holdings;
  }

  public OfficeLocation getPickupLocation() {
    return pickupLocation;
  }

  public void setPickupLocation(OfficeLocation pickupLocation) {
    this.pickupLocation = pickupLocation;
  }

  public OfficeLocation getProcessingLocation() {
    return processingLocation;
  }

  public void setProcessingLocation(OfficeLocation processingLocation) {
    this.processingLocation = processingLocation;
  }

  public Patron getPatron() {
    return patron;
  }

  public void setPatron(Patron patron) {
    this.patron = patron;
  }

  public PatronComments getPatronComments() {
    return patronComments;
  }

  public void setPatronComments(PatronComments patronComments) {
    this.patronComments = patronComments;
  }

  public String getRequestSource() {
    return requestSource;
  }

  public void setRequestSource(String requestSource) {
    this.requestSource = requestSource;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 61 * hash + Objects.hashCode(this.dispatchMethod);
    hash = 61 * hash + Objects.hashCode(this.holdings);
    hash = 61 * hash + Objects.hashCode(this.pickupLocation);
    hash = 61 * hash + Objects.hashCode(this.processingLocation);
    hash = 61 * hash + Objects.hashCode(this.patron);
    hash = 61 * hash + Objects.hashCode(this.patronComments);
    hash = 61 * hash + Objects.hashCode(this.requestSource);
    hash = 61 * hash + Objects.hashCode(this.metadata);
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
    final FulfillmentRequest other = (FulfillmentRequest) obj;
    if (!Objects.equals(this.requestSource, other.requestSource)) {
      return false;
    }
    if (this.dispatchMethod != other.dispatchMethod) {
      return false;
    }
    if (!Objects.equals(this.holdings, other.holdings)) {
      return false;
    }
    if (!Objects.equals(this.pickupLocation, other.pickupLocation)) {
      return false;
    }
    if (!Objects.equals(this.processingLocation, other.processingLocation)) {
      return false;
    }
    if (!Objects.equals(this.patron, other.patron)) {
      return false;
    }
    if (!Objects.equals(this.patronComments, other.patronComments)) {
      return false;
    }
    if (!Objects.equals(this.metadata, other.metadata)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "FulfillmentRequest{" + "dispatchMethod=" + dispatchMethod + ", holdings=" + holdings + ", pickupLocation=" + pickupLocation + ", processingLocation=" + processingLocation + ", patron=" + patron + ", patronComments=" + patronComments + ", requestSource=" + requestSource + ", metadata=" + metadata + '}';
  }

}
