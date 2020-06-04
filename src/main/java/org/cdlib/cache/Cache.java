package org.cdlib.cache;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Cache<T, S> extends LinkedHashMap<T, S> {
    
    private int maxSize;
    
    public Cache(int cacheSize) {
      super();
      maxSize = cacheSize;
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<T, S> eldest) {
      return size() > maxSize;
    }
}
