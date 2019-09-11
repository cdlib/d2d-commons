package org.cdlib.domain.objects.bib;

import org.cdlib.util.JSON;

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
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Title other = (Title) obj;
      if (canonical == null) {
        if (other.canonical != null)
          return false;
      } else if (!canonical.equals(other.canonical))
        return false;
      if (abbr == null) {
        if (other.abbr != null)
          return false;
      } else if (!abbr.equals(other.abbr))
        return false;
      if (key == null) {
        if (other.key != null)
          return false;
      } else if (!key.equals(other.key))
        return false;
      if (uniform == null) {
        if (other.uniform != null)
          return false;
      } else if (!uniform.equals(other.uniform))
        return false;
      return true;
    }
    
    @Override
    public String toString() {
      return JSON.serialize(this);
    }

}
