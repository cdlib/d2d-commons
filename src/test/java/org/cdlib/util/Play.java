package org.cdlib.util;

import static org.junit.Assert.assertEquals;
import org.cdlib.domain.objects.bib.Carrier;
import org.cdlib.domain.objects.bib.CarrierClass;
import org.junit.Test;

public class Play {
  
  @Test
  public void test() {
    assertEquals(CarrierClass.PHYSICAL, Carrier.PRINT.carrierClass());
  }
  
  

}
