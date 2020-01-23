package org.cdlib.util;

import java.util.*;

/**
 * StringDelimitizer is like StringTokenizer except that empty strings between 
 * occurences of the delimiter will be returned
 */
 
public class StringDelimitizer implements Enumeration<Object> {

	private StringTokenizer st;
	private String delimiters;

	public StringDelimitizer (String s, String d) {
		delimiters = d;
		st = new StringTokenizer (s, d, true);
	}
	
 	public boolean hasMoreTokens () {
 		return st.hasMoreTokens();
 	}
 	
 	public String nextToken () {
 		String work = "";
 		String next = st.nextToken();
 		if (!delimiters.contains(next)) {
 			work = next;
 			if (st.hasMoreElements())
 				next = st.nextToken();
 		}
 		return work;
 	}
 	
 	public String nextToken (String d) {
 		return nextToken ();
 	}
 	
 	public boolean hasMoreElements () {
 		return this.hasMoreTokens();
 	}
 	
 	public Object nextElement () {
 		return this.nextToken();
 	}
 	
}