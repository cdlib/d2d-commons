package org.cdlib.cache;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
class Cache<T> extends LinkedHashMap<T, String> {
    
    private int maxSize;
    
    public Cache(int cacheSize) {
      super();
      maxSize = cacheSize;
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<T, String> eldest) {
      return size() > maxSize;
    }
}
