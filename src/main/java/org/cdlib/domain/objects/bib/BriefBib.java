package org.cdlib.domain.objects.bib;

import java.net.URI;
import java.util.List;

/*
 * An abbreviated representation of a Bib that is 
 * used for linking to full bibs.
 * 
 * The brief bib contains enough data to support
 * decisions about whether to link to the full Bib.
 */
public class BriefBib {
    private Carrier carrier;
    private List<Identifier> identifiers;
    private Title title;
    private URI uri; 
    
    public Carrier getCarrier() {
        return carrier;
    }
    
    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public Title getTitle() {
        return title;
    }

    public URI getURI() {
        return uri;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setURI(URI uri) {
        this.uri = uri;
    }
    
    
}
