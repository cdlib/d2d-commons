package org.cdlib.cache;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class CacheService<T, S> {
    
    protected Map<T, S> cache;
    
    protected int DEFAULT_CACHE_SIZE = 60;
    
    public void init() {
      int cacheSize = DEFAULT_CACHE_SIZE;
      cache = Collections.synchronizedMap(new Cache<T, S>(cacheSize));
    }
    
    public void insertInto(T key, S result) {
      cache.put(key, result);
    }
    
    public Optional<S> getFrom(T key) {
      return Optional.ofNullable(cache.get(key));
    }
    
    public void clear() {
      init();
    }
    
    public int size() {
      return cache.size();
    }
}
