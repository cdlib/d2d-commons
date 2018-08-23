package org.cdlib.holdings;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/*
 * Defines test data and expected results.
 * Serializable data is loaded from test_holdings.xml.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestHoldingsItem implements Serializable {

    @XmlElement
    public String holdings;
    @XmlElement
    public Integer earliestYear;
    @XmlElement
    public Integer latestYear;

}
