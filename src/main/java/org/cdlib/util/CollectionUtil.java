/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cdlib.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Miscellaneous static helper methods for transforming maps.
 * 
 * @author jferrie
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * Takes a Map of String to String[] and returns a Map of String to String
     * using only the first string found in the string array value. This is used
     * to process a URL query string that is expected to be a String to String
     * key value pair. If a String[] value is found in the input map, this
     * method logs a warning and ignores all elements in the array except the
     * first.
     * 
     * @param stringArrayMap
     *            normally a URL query string
     * @return a Map<String, String> using only the first element in the array
     *         of String
     * @throws NullPointerException
     *             if stringArrayMap is null
     */
    public static Map<String, String> asSingleStringMap(Map<String, String[]> stringArrayMap) {
        Map<String, String> singleStringMap = new HashMap<String, String>();
        if (stringArrayMap == null) {
            throw new NullPointerException("stringArrayMap cannot be null");
        }
        for (String key : stringArrayMap.keySet()) {
            String[] sArray = (String[]) stringArrayMap.get(key);
            if (sArray.length > 1) {
            }
            singleStringMap.put(key, stringArrayMap.get(key)[0]);
        }
        return singleStringMap;
    }

    /**
     * Given a Map<?, String>, returns a corresponding Map<String, String> This
     * is needed, for example, for passing to JSP, which cannot get a value from
     * an EnumMap for display.
     * 
     * @param inMap
     * @return
     */
    public static Map<String, String> toStringKeyMap(Map<?, String> inMap) {

        if (inMap == null) {
            throw new NullPointerException("Null parameter not allowed.");
        }
        Map<String, String> outMap = new HashMap<String, String>();
        for (Object key : inMap.keySet()) {
            outMap.put(key.toString(), (String) inMap.get(key));
        }
        return outMap;
    }

    /**
     * Constructs a map from a string representing the map key/value pairs.
     * 
     * @param mapString
     *            the string representing the key/value pairs for example:
     *            "p1=foo;p2=bar"
     * @param itemSeparator
     *            the separator regex for items in the map, for example ";"
     * @param keyValSeparator
     *            the separator between the key and value, for example "="
     */
    public static Map<String, String> stringToMap(String mapString, String itemSeparator, String keyValSeparator) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        String[] items = mapString.split(itemSeparator);
        for (String item : items) {
            String[] keyValPair = item.split(keyValSeparator);
            if (keyValPair.length != 2) {
                throw new IllegalArgumentException("String representing map is malformed");
            }
            map.put(keyValPair[0], keyValPair[1]);
        }
        return map;
    }

    /**
     * Removes all items from a map that have null values. Returns a new map;
     * does not modify input map in place.
     */
    public static <T, T1> Map<T, T1> removeNullItems(Map<T, T1> inMap) {
        Map<T, T1> outMap = new LinkedHashMap<T, T1>();
        for (T key : inMap.keySet()) {
            if (inMap.get(key) != null) {
                outMap.put(key, inMap.get(key));
            }
        }
        return outMap;
    }
    
        /**
     * Removes all items from a map that have null values. Returns a new map;
     * does not modify input map in place.
     */
    public static <T, T1> Map<T, T1> removeEmptyItems(Map<T, T1> inMap) {
        Map<T, T1> outMap = new LinkedHashMap<T, T1>();
        for (T key : inMap.keySet()) {
            if (inMap.get(key) != null && !inMap.get(key).toString().matches("^\\s*$")) {
                outMap.put(key, inMap.get(key));
            }
        }
        return outMap;
    }

    public static <T> Map<T, String> toStringMap(Map<T, Object> inMap) {
        Map<T, String> outMap = new HashMap<T, String>();
        for (T key : inMap.keySet()) {
            if (inMap.get(key) != null) {
                outMap.put(key, inMap.get(key).toString());
            } 
        }
        return outMap;
    }
    
    /**
     * Returns a Set based on varargs set members
     */
    public static <T> Set<T> makeSet(T... members) {
      Set<T> hs =  new HashSet<T>();
      for (T member : members) {
        hs.add(member);
      }
      return hs;
    }
    
    /** 
     * 
     * @param list that has possible duplicate values
     * @return a deduplicated list
     */
    public static <T> List<T> dedupedList(List<T> list) {
      List<T> deduped = new ArrayList<>();
      for (T o : list) {
        if (!deduped.contains(o)) {
          deduped.add(o);
        }
      }
      return deduped;
    }

}
