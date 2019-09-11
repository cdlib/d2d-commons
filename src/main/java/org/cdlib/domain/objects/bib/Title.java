package org.cdlib.domain.objects.bib;

public class Title {

    private String canonical; //245
    private String abbr; //210
    private String key; //222
    private String uniform; //240
    
    public String getCanonical() {
        return canonical;
    }
    
    public void setCanonical(String canonical) {
        this.canonical = canonical;
    }
    
    public String getAbbr() {
        return abbr;
    }
    
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getUniform() {
        return uniform;
    }
    
    public void setUniform(String uniform) {
        this.uniform = uniform;
    }
    
}
