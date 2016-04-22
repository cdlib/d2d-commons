package org.cdlib.services.hparser;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A JAXB-serializable list of holdings record objects.
 * This is used for marshalling and unmarshalling test data.
 * 
 * Test data is loaded from test_holdings.xml.
 * 
 * @author jferrie
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestHoldingsList implements Serializable {

    @XmlElementRef(name = "testHoldingsList")
    public List<TestHoldingsItem> testHoldingsList;

}
