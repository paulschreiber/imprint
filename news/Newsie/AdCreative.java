// AdCreative.java
// Created on Tue Sep 18 00:21:57 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class AdCreative extends EOGenericRecord {

    public AdCreative() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public AdCreative(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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
	// return the advertisement name, concatenated with the advertiser's
	// company name
	//
    public String nameAndCompany() {
        String name = (String)storedValueForKey("name");
		String company = advertiser().company();
		return name + " (" + company + ")";
    }

    public String location() {
        return (String)storedValueForKey("location");
    }

    public void setLocation(String value) {
        takeStoredValueForKey(value, "location");
    }

    public String name() {
        return (String)storedValueForKey("name");
    }

    public void setName(String value) {
        takeStoredValueForKey(value, "name");
    }

    public Number issueId() {
        return (Number)storedValueForKey("issueId");
    }

    public void setIssueId(Number value) {
        takeStoredValueForKey(value, "issueId");
    }

    public Number width() {
        return (Number)storedValueForKey("width");
    }

    public void setWidth(Number value) {
        takeStoredValueForKey(value, "width");
    }

    public Number height() {
        return (Number)storedValueForKey("height");
    }

    public void setHeight(Number value) {
        takeStoredValueForKey(value, "height");
    }

    public String url() {
        return (String)storedValueForKey("url");
    }

    public void setUrl(String value) {
        takeStoredValueForKey(value, "url");
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

    public NSArray adplacements() {
        return (NSArray)storedValueForKey("adplacements");
    }

    public void setAdplacements(NSMutableArray value) {
        takeStoredValueForKey(value, "adplacements");
    }

    public void addToAdplacements(AdPlacement object) {
        NSMutableArray array = (NSMutableArray)adplacements();

        willChange();
        array.addObject(object);
    }

    public void removeFromAdplacements(AdPlacement object) {
        NSMutableArray array = (NSMutableArray)adplacements();

        willChange();
        array.removeObject(object);
    }
}
