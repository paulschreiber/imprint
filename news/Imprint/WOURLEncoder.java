//
//  WOURLEncoder.java
//  Imprint
//
//  Created by paul on Sun Oct 14 2001.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//

/* WOURLEncoder - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package com.webobjects.appserver._private;
import java.util.Enumeration;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

public class WOURLEncoder
{
    private static final char[][] encoding;
    
    public static String encode(String string) {
		int i = string.length();
		StringBuffer stringbuffer = new StringBuffer(i * 2);
		try {
			for (int i0 = 0; i0 < i; i0++)
			stringbuffer.append(encoding[string.charAt(i0)]);
		} catch (IndexOutOfBoundsException indexoutofboundsexception) {
			throw new IllegalStateException
				("URL may only contain 8-Bit characters!");
		}
		return new String(stringbuffer);
    }
    
    public static String encodeAsCGIFormValues(NSDictionary nsdictionary) {
		NSMutableArray nsmutablearray
			= _encodeAsCGIFormValues(nsdictionary, null);
		//AK Was:
		// String string = nsmutablearray.componentsJoinedByString("&");
		String string = nsmutablearray.componentsJoinedByString("&amp;");
		return string;
    }
    
    private static NSMutableArray _encodeAsCGIFormValues(Object object,
                             String string) {
		NSMutableArray nsmutablearray = null;
		if (object != null) {
			if (object instanceof NSDictionary)
			nsmutablearray
				= _encodeAsCGIFormValues((NSDictionary) object, string);
			else if (object instanceof NSArray)
			nsmutablearray
				= _encodeAsCGIFormValues((NSArray) object, string);
			else {
			String string1 = object.toString();
			String string2 = encode(string1);
			String string3 = string + "=" + string2;
			nsmutablearray = new NSMutableArray();
			nsmutablearray.addObject(string3);
			}
		}
		return nsmutablearray;
    }
    
    private static NSMutableArray _encodeAsCGIFormValues
    (NSDictionary nsdictionary, String string) {
		Enumeration enumeration = nsdictionary.keyEnumerator();
		NSMutableArray nsmutablearray
			= new NSMutableArray(nsdictionary.count());
		while (enumeration.hasMoreElements()) {
			String string4 = (String) enumeration.nextElement();
			Object object = nsdictionary.objectForKey(string4);
			String string5 = encode(string4);
			String string6;
			if (string != null)
			string6 = string + "." + string5;
			else
			string6 = string5;
			NSMutableArray nsmutablearray7
			= _encodeAsCGIFormValues(object, string6);
			nsmutablearray.addObjectsFromArray(nsmutablearray7);
		}
		return nsmutablearray;
    }
    
    private static NSMutableArray _encodeAsCGIFormValues(NSArray nsarray,
                             String string) {
		int i = nsarray.count();
		NSMutableArray nsmutablearray = new NSMutableArray(i);
		for (int i8 = 0; i8 < i; i8++) {
			Object object = nsarray.objectAtIndex(i8);
			NSMutableArray nsmutablearray9
			= _encodeAsCGIFormValues(object, string);
			nsmutablearray.addObjectsFromArray(nsmutablearray9);
		}
		return nsmutablearray;
    }
    
    static {
		char[] cs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		encoding = new char[256][];
	
		for (char c = '\0'; c < 256; c++) {
			if (c >= 97 && c <= 122 || c >= 65 && c <= 90 || c >= 48 && c <= 57
			|| c == 45 || c == 95 || c == 46 || c == 42 || c == 32) {
				if (c == 32)
					encoding[c] = new char[] { '+' };
				else
					encoding[c] = new char[] { c };
			}
			else
				encoding[c] = new char[] { '%', cs[c >> 4], cs[c & 0xf] };
		}
    } // static
}
