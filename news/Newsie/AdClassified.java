// AdClassified.java
// Created on Wed Aug 22 18:13:49 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class AdClassified extends EOGenericRecord {

    public AdClassified() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public AdClassified(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
        super(context, classDesc, gid);
    }

    // If you add instance variables to store property values you
    // should add empty implementions of the Serialization methods
    // to avoid unnecessary overhead (the properties will be
    // serialized for you in the superclass).
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    }
*/

	//
	// return the first AdPrefixSize() characters of the ad,
	// or return the whole ad if it has fewer than
	// AdPrefixSize() characters
	//
    public String adPrefix() {
        String result = (String)storedValueForKey("ad");
		int prefixLength = Helpers.AdPrefixSize();
		
		// avoid string out-of-bounds errors
		if ( result.length() < prefixLength ) {
			prefixLength = result.length();
		}
		
		result = result.substring(0, prefixLength);
		return result;
	}

	//
	// return a href to this section front, relative to the web server root,
	// referencing the Imprint.woa app (front end)
	//
	public String link() {
		String sectionName = adSection().sectionName();
		String date = issue().compactDate();
		return ( new String ( Helpers.BaseClassifiedsUrl() + sectionName + "&amp;date=" + date ) );
	}

	//
	// return a href to this section front, relative to the web server root,
	// referencing the Adverts.woa app (end end)
	//
	public String linkPreview() {
		String sectionName = adSection().sectionName();
		String date = issue().compactDate();
		
		return ( new String ( Helpers.BaseClassifiedsPreviewUrl() + sectionName + "&amp;date=" + date ) );
	}
	
    public String ad() {
        return (String)storedValueForKey("ad");
    }

    public void setAd(String value) {
        takeStoredValueForKey(value, "ad");
    }

    public Number id() {
        return (Number)storedValueForKey("id");
    }

    public void setId(Number value) {
        takeStoredValueForKey(value, "id");
    }

    public Advertiser advertiser() {
        return (Advertiser)storedValueForKey("advertiser");
    }

    public void setAdvertiser(Advertiser value) {
        takeStoredValueForKey(value, "advertiser");
    }

    public Issue issue() {
        return (Issue)storedValueForKey("issue");
    }

    public void setIssue(Issue value) {
        takeStoredValueForKey(value, "issue");
    }

    public AdSection adSection() {
        return (AdSection)storedValueForKey("adSection");
    }

    public void setAdSection(AdSection value) {
        takeStoredValueForKey(value, "adSection");
    }
}
