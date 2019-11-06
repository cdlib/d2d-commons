package org.cdlib.domain.objects.bib;

import java.net.URL;
import java.util.List;

public class CachedBib {
    private URL url;
    private Carrier carrier;
    private List<Identifier> idList;
    private Title title; 
    
    public Carrier getCarrier() {
        return carrier;
    }
    
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public List<Identifier> getIdList() {
        return idList;
    }

    public void setIdList(List<Identifier> idList) {
        this.idList = idList;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
