// IssueFront.java
// Created on Wed Aug 29 22:16:40 US/Eastern 2001 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class IssueFront extends EOGenericRecord {

    public IssueFront() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public IssueFront(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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
	// return a href to this issue front, relative to the web server root,
	// referencing the Imprint.woa app (front end)
	//
	public String link() {
		String date = issue().compactDate();
		return ( new String ( Helpers.BaseIssueUrl() + date ) );
	}

	//
	// return a href to this issue front, relative to the web server root,
	// referencing the Newsie.woa app (back end)
	//
	public String linkPreview() {
		String date = issue().compactDate();
		
		return ( new String ( Helpers.BaseIssuePreviewUrl() + date ) );
	}

	//
	// return a href to the text only version of this issue, relative
	// to the web server root, referencing the Imprint.woa app (front end)
	//
	public String linkLite() {
		String date = issue().compactDate();
		
		return ( new String ( Helpers.BaseIssueLiteUrl() + date ) );
	}


    public String content() {
        return (String)storedValueForKey("content");
    }

    public void setContent(String value) {
        takeStoredValueForKey(value, "content");
    }

    public Issue issue() {
        return (Issue)storedValueForKey("issue");
    }

    public void setIssue(Issue value) {
        takeStoredValueForKey(value, "issue");
    }
}
